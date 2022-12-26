package com.joy187.re8gun;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joy187.re8gun.item.RE8GunItem;
import com.mrcrayfish.framework.api.data.login.ILoginData;
import com.mrcrayfish.guns.GunMod;
import com.mrcrayfish.guns.annotation.Validator;
import com.mrcrayfish.guns.common.GripType;
import com.mrcrayfish.guns.common.Gun;
import com.mrcrayfish.guns.common.JsonDeserializers;
import com.mrcrayfish.guns.network.PacketHandler;
import com.mrcrayfish.guns.network.message.MessageUpdateGuns;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Mod.EventBusSubscriber(
        modid = Main.MOD_ID
)
public class RE8NetworkGunManager extends SimplePreparableReloadListener<Map<RE8GunItem, Gun>> {
    private static final int FILE_TYPE_LENGTH_VALUE = ".json".length();
    private static final Gson GSON_INSTANCE = (Gson) Util.make(() -> {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ResourceLocation.class, JsonDeserializers.RESOURCE_LOCATION);
        builder.registerTypeAdapter(GripType.class, JsonDeserializers.GRIP_TYPE);
        return builder.create();
    });
    private static List<RE8GunItem> clientRegisteredGuns = new ArrayList();
    private static RE8NetworkGunManager instance;
    private Map<ResourceLocation, Gun> registeredGuns = new HashMap();

    public RE8NetworkGunManager() {
    }

    protected Map<RE8GunItem, Gun> prepare(ResourceManager manager, ProfilerFiller profiler) {
        Map<RE8GunItem, Gun> map = new HashMap();
        ForgeRegistries.ITEMS.getValues().stream().filter((item) -> {
            return item instanceof RE8GunItem;
        }).forEach((item) -> {
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
            if (id != null) {
                List<ResourceLocation> resources = new ArrayList(manager.listResources("guns", (fileName) -> {
                    return fileName.getPath().endsWith(id.getPath() + ".json");
                }).keySet());
                resources.sort((r1, r2) -> {
                    if (r1.getNamespace().equals(r2.getNamespace())) {
                        return 0;
                    } else {
                        return r2.getNamespace().equals(Main.MOD_ID) ? 1 : -1;
                    }
                });
                resources.forEach((resourceLocation) -> {
                    String path = resourceLocation.getPath().substring(0, resourceLocation.getPath().length() - FILE_TYPE_LENGTH_VALUE);
                    String[] splitPath = path.split("/");
                    if (id.getPath().equals(splitPath[splitPath.length - 1])) {
                        manager.getResource(resourceLocation).ifPresent((resource) -> {
                            try {
                                BufferedReader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8));

                                try {
                                    Gun gun = (Gun) GsonHelper.fromJson(GSON_INSTANCE, reader, Gun.class);
                                    if (gun != null && Validator.isValidObject(gun)) {
                                        map.put((RE8GunItem)item, gun);
                                    } else {
                                        GunMod.LOGGER.error("Couldn't load data file {} as it is missing or malformed. Using default gun data", resourceLocation);
                                        map.putIfAbsent((RE8GunItem)item, new Gun());
                                    }
                                } catch (Throwable var8) {
                                    try {
                                        reader.close();
                                    } catch (Throwable var7) {
                                        var8.addSuppressed(var7);
                                    }

                                    throw var8;
                                }

                                reader.close();
                            } catch (InvalidObjectException var9) {
                                GunMod.LOGGER.error("Missing required properties for {}", resourceLocation);
                                var9.printStackTrace();
                            } catch (IOException var10) {
                                GunMod.LOGGER.error("Couldn't parse data file {}", resourceLocation);
                            } catch (IllegalAccessException var11) {
                                var11.printStackTrace();
                            }

                        });
                    }
                });
            }

        });
        return map;
    }

    protected void apply(Map<RE8GunItem, Gun> objects, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<ResourceLocation, Gun> builder = ImmutableMap.builder();
        objects.forEach((item, gun) -> {
            builder.put((ResourceLocation) Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)), gun);
            item.setGun(new RE8NetworkGunManager.Supplier(gun));
        });
        this.registeredGuns = builder.build();
    }

    public void writeRegisteredGuns(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.registeredGuns.size());
        this.registeredGuns.forEach((id, gun) -> {
            buffer.writeResourceLocation(id);
            buffer.writeNbt(gun.serializeNBT());
        });
    }

    public static ImmutableMap<ResourceLocation, Gun> readRegisteredGuns(FriendlyByteBuf buffer) {
        int size = buffer.readVarInt();
        if (size <= 0) {
            return ImmutableMap.of();
        } else {
            ImmutableMap.Builder<ResourceLocation, Gun> builder = ImmutableMap.builder();

            for(int i = 0; i < size; ++i) {
                ResourceLocation id = buffer.readResourceLocation();
                Gun gun = Gun.create(buffer.readNbt());
                builder.put(id, gun);
            }

            return builder.build();
        }
    }

    public static boolean updateRegisteredGuns(MessageUpdateGuns message) {
        return updateRegisteredGuns((Map)message.getRegisteredGuns());
    }

    private static boolean updateRegisteredGuns(Map<ResourceLocation, Gun> registeredGuns) {
        clientRegisteredGuns.clear();
        if (registeredGuns != null) {
            Iterator var1 = registeredGuns.entrySet().iterator();

            while(var1.hasNext()) {
                Map.Entry<ResourceLocation, Gun> entry = (Map.Entry)var1.next();
                Item item = (Item)ForgeRegistries.ITEMS.getValue((ResourceLocation)entry.getKey());
                if (!(item instanceof RE8GunItem)) {
                    return false;
                }

                ((RE8GunItem)item).setGun(new RE8NetworkGunManager.Supplier((Gun)entry.getValue()));
                clientRegisteredGuns.add((RE8GunItem)item);
            }

            return true;
        } else {
            return false;
        }
    }

    public Map<ResourceLocation, Gun> getRegisteredGuns() {
        return this.registeredGuns;
    }

    public static List<RE8GunItem> getClientRegisteredGuns() {
        return ImmutableList.copyOf(clientRegisteredGuns);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        instance = null;
    }

    @SubscribeEvent
    public static void addReloadListenerEvent(AddReloadListenerEvent event) {
        RE8NetworkGunManager networkGunManager = new RE8NetworkGunManager();
        event.addListener(networkGunManager);
        instance = networkGunManager;
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) {
            PacketHandler.getPlayChannel().send(PacketDistributor.ALL.noArg(), new MessageUpdateGuns());
        }

    }

    @Nullable
    public static RE8NetworkGunManager get() {
        return instance;
    }

    public static class Supplier {
        private Gun gun;

        private Supplier(Gun gun) {
            this.gun = gun;
        }

        public Gun getGun() {
            return this.gun;
        }
    }

    public static class LoginData implements ILoginData {
        public LoginData() {
        }

        public void writeData(FriendlyByteBuf buffer) {
            Validate.notNull(RE8NetworkGunManager.get());
            RE8NetworkGunManager.get().writeRegisteredGuns(buffer);
        }

        public Optional<String> readData(FriendlyByteBuf buffer) {
            Map<ResourceLocation, Gun> registeredGuns = RE8NetworkGunManager.readRegisteredGuns(buffer);
            RE8NetworkGunManager.updateRegisteredGuns(registeredGuns);
            return Optional.empty();
        }
    }
}
