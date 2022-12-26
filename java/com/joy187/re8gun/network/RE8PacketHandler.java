package com.joy187.re8gun.network;

import com.joy187.re8gun.Main;
import com.mrcrayfish.framework.api.event.FrameworkEvent;
import com.mrcrayfish.framework.api.network.FrameworkChannelBuilder;
import com.mrcrayfish.framework.network.message.IMessage;
import com.mrcrayfish.guns.common.NetworkGunManager;
import com.mrcrayfish.guns.network.message.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

//public class RE8PacketHandler {
//    public static final String PROTOCOL_VERSION = "1";
//    private static SimpleChannel handshakeChannel;
//    private static SimpleChannel playChannel;
//    private static int nextMessageId = 0;
//
//    public static void init()
//    {
//
//        registerPlayMessage(RE8MessageCraft.class, RE8MessageCraft::new, LogicalSide.SERVER);
//    }
//
//    private static <T extends IMessage> void registerPlayMessage(Class<T> clazz, Supplier<T> messageSupplier, LogicalSide side)
//    {
//        playChannel.registerMessage(nextMessageId++, clazz, IMessage::encode, buffer -> {
//            T t = messageSupplier.get();
//            t.decode(buffer);
//            return t;
//        }, (t, supplier) -> {
//            if(supplier.get().getDirection().getReceptionSide() != side)
//                throw new RuntimeException("Attempted to handle message " + clazz.getSimpleName() + " on the wrong logical side!");
//            t.handle(supplier);
//        });
//    }
//
//}
public class RE8PacketHandler {
    private static final SimpleChannel PLAY_CHANNEL1;

    public RE8PacketHandler() {
    }

    public static SimpleChannel getPlayChannel() {
        return PLAY_CHANNEL1;
    }

    public static void onFrameworkRegister(FrameworkEvent.Register event) {
        event.registerLoginData(new ResourceLocation(Main.MOD_ID, "network_gun_manager"), NetworkGunManager.LoginData::new);
        event.registerLoginData(new ResourceLocation(Main.MOD_ID, "custom_gun_manager"), com.mrcrayfish.guns.client.CustomGunManager.LoginData::new);
    }

    static {
        PLAY_CHANNEL1 = FrameworkChannelBuilder.create(Main.MOD_ID, "re8play", 2)
                .registerPlayMessage(RE8MessageCraft.class, NetworkDirection.PLAY_TO_SERVER).build();

//                .registerPlayMessage(MessageAim.class, NetworkDirection.PLAY_TO_SERVER)
//                .registerPlayMessage(MessageReload.class, NetworkDirection.PLAY_TO_SERVER)
//                .registerPlayMessage(MessageShoot.class, NetworkDirection.PLAY_TO_SERVER)
//                .registerPlayMessage(MessageUnload.class, NetworkDirection.PLAY_TO_SERVER)
//                .registerPlayMessage(MessageStunGrenade.class, NetworkDirection.PLAY_TO_CLIENT)
//                .registerPlayMessage(MessageCraft.class, NetworkDirection.PLAY_TO_SERVER)
//                .registerPlayMessage(MessageBulletTrail.class, NetworkDirection.PLAY_TO_CLIENT)
//                .registerPlayMessage(MessageAttachments.class, NetworkDirection.PLAY_TO_SERVER)
//                .registerPlayMessage(MessageUpdateGuns.class, NetworkDirection.PLAY_TO_CLIENT)
//                .registerPlayMessage(MessageBlood.class, NetworkDirection.PLAY_TO_CLIENT).registerPlayMessage(MessageShooting.class, NetworkDirection.PLAY_TO_SERVER).registerPlayMessage(MessageGunSound.class, NetworkDirection.PLAY_TO_CLIENT).registerPlayMessage(MessageProjectileHitBlock.class, NetworkDirection.PLAY_TO_CLIENT).registerPlayMessage(MessageProjectileHitEntity.class, NetworkDirection.PLAY_TO_CLIENT).registerPlayMessage(MessageRemoveProjectile.class, NetworkDirection.PLAY_TO_CLIENT).build();

    }
}
