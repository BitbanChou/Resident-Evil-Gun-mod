package com.joy187.re8gun.block;

import com.google.common.collect.ImmutableList;
import com.joy187.re8gun.Main;
import com.joy187.re8gun.RE8NetworkGunManager;
import com.joy187.re8gun.init.ItemInit;
import com.joy187.re8gun.item.RE8GunItem;
import com.joy187.re8gun.network.RE8MessageCraft;
import com.joy187.re8gun.network.RE8PacketHandler;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mrcrayfish.guns.client.screen.CheckBox;
import com.mrcrayfish.guns.client.util.RenderUtil;
import com.mrcrayfish.guns.item.GunItem;
import com.mrcrayfish.guns.item.IAmmo;
import com.mrcrayfish.guns.item.IColored;
import com.mrcrayfish.guns.item.attachment.IAttachment;
import com.mrcrayfish.guns.network.PacketHandler;
import com.mrcrayfish.guns.network.message.MessageCraft;
import com.mrcrayfish.guns.util.InventoryUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.opengl.GL11;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RE8WorkbenchScreen extends AbstractContainerScreen<RE8WorkbenchContainer> {
    //private static final ResourceLocation GUI_BASE = new ResourceLocation("cgm:textures/gui/workbench.png");
    private static final ResourceLocation GUI_BASE =
            new ResourceLocation(Main.MOD_ID, "textures/gui/workbench.png");
    private static boolean showRemaining = false;
    private Tab currentTab;
    private List<Tab> tabs = new ArrayList();
    private List<MaterialItem> materials;
    private List<MaterialItem> filteredMaterials;
    private Inventory playerInventory;
    private RE8WorkbenchBlockEntity workbench;
    private Button btnCraft;
    private CheckBox checkBoxMaterials;
    private ItemStack displayStack;

    public RE8WorkbenchScreen(RE8WorkbenchContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
        this.playerInventory = playerInventory;
        this.workbench = container.getWorkbench();
        this.imageWidth = 275;
        this.imageHeight = 184;
        this.materials = new ArrayList();
        this.createTabs(RE8WorkbenchRecipes.getAll(playerInventory.player.level));
        if (!this.tabs.isEmpty()) {
            this.imageHeight += 28;
        }

    }

    private void createTabs(NonNullList<RE8WorkbenchRecipe> recipes) {
        List<RE8WorkbenchRecipe> weapons = new ArrayList();
        List<RE8WorkbenchRecipe> attachments = new ArrayList();
        List<RE8WorkbenchRecipe> ammo = new ArrayList();
        List<RE8WorkbenchRecipe> misc = new ArrayList();
        Iterator var6 = recipes.iterator();

        while(var6.hasNext()) {
            RE8WorkbenchRecipe recipe = (RE8WorkbenchRecipe)var6.next();
            ItemStack output = recipe.getItem();
            if (output.getItem() instanceof GunItem) {
                weapons.add(recipe);
            } else if (output.getItem() instanceof IAttachment) {
                attachments.add(recipe);
            } else if (this.isAmmo(output)) {
                ammo.add(recipe);
            } else {
                misc.add(recipe);
            }
        }

        if (!weapons.isEmpty()) {
            ItemStack icon = new ItemStack(ItemInit.GM79.get());
            icon.getOrCreateTag().putInt("AmmoCount", (ItemInit.GM79.get()).getGun().getGeneral().getMaxAmmo());
            this.tabs.add(new RE8WorkbenchScreen.Tab(icon, "weapons", weapons));
        }

        if (!attachments.isEmpty()) {
            this.tabs.add(new RE8WorkbenchScreen.Tab(new ItemStack(ItemInit.LONGRANGE_8x_SCOPE.get()), "attachments", attachments));
        }

        if (!ammo.isEmpty()) {
            this.tabs.add(new RE8WorkbenchScreen.Tab(new ItemStack(ItemInit.SNIPERAMMO.get()), "ammo", ammo));
        }

//        if (!misc.isEmpty()) {
//            this.tabs.add(new RE8WorkbenchScreen.Tab(new ItemStack(Items.BARRIER), "misc", misc));
//        }

        if (!this.tabs.isEmpty()) {
            this.currentTab = this.tabs.get(0);
        }

    }

    private boolean isAmmo(ItemStack stack) {
        if (stack.getItem() instanceof IAmmo) {
            return true;
        } else {
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
            Objects.requireNonNull(id);
            Iterator var3 = RE8NetworkGunManager.getClientRegisteredGuns().iterator();

            RE8GunItem gunItem;
            do {
                if (!var3.hasNext()) {
                    return false;
                }

                gunItem = (RE8GunItem)var3.next();
            } while(!id.equals(gunItem.getModifiedGun(stack).getProjectile().getItem()));

            return true;
        }
    }

    public void init() {
        super.init();
        if (!this.tabs.isEmpty()) {
            this.topPos += 28;
        }

        this.addRenderableWidget(new Button(this.leftPos + 9, this.topPos + 18, 15, 20, Component.literal("<"), (button) -> {
            int index = this.currentTab.getCurrentIndex();
            if (index - 1 < 0) {
                this.loadItem(this.currentTab.getRecipes().size() - 1);
            } else {
                this.loadItem(index - 1);
            }

        }));
        this.addRenderableWidget(new Button(this.leftPos + 153, this.topPos + 18, 15, 20, Component.literal(">"), (button) -> {
            int index = this.currentTab.getCurrentIndex();
            if (index + 1 >= this.currentTab.getRecipes().size()) {
                this.loadItem(0);
            } else {
                this.loadItem(index + 1);
            }

        }));
        this.btnCraft = this.addRenderableWidget(new Button(this.leftPos + 195, this.topPos + 16, 74, 20, Component.translatable("gui.re8gun.re8workbench.assemble"), (button) -> {
            int index = this.currentTab.getCurrentIndex();
            RE8WorkbenchRecipe recipe = this.currentTab.getRecipes().get(index);
            ResourceLocation registryName = recipe.getId();
            RE8PacketHandler.getPlayChannel().sendToServer(new RE8MessageCraft(registryName, this.workbench.getBlockPos()));
        }));
        this.btnCraft.active = false;
        this.checkBoxMaterials = this.addRenderableWidget(new CheckBox(this.leftPos + 172, this.topPos + 51, Component.translatable("gui.re8gun.re8workbench.show_remaining")));
        this.checkBoxMaterials.setToggled(showRemaining);
        this.loadItem(this.currentTab.getCurrentIndex());
    }

    public void containerTick() {
        super.containerTick();
        Iterator var1 = this.materials.iterator();

        while(var1.hasNext()) {
            MaterialItem material = (RE8WorkbenchScreen.MaterialItem)var1.next();
            material.tick();
        }

        boolean canCraft = true;
        Iterator var5 = this.materials.iterator();

        while(var5.hasNext()) {
            MaterialItem material = (RE8WorkbenchScreen.MaterialItem)var5.next();
            if (!material.isEnabled()) {
                canCraft = false;
                break;
            }
        }

        this.btnCraft.active = canCraft;
        this.updateColor();
    }

    private void updateColor() {
        if (this.currentTab != null) {
            ItemStack item = this.displayStack;
            if (IColored.isDyeable(item)) {
                IColored colored = (IColored)item.getItem();
                if (!this.workbench.getItem(0).isEmpty()) {
                    ItemStack dyeStack = this.workbench.getItem(0);
                    if (dyeStack.getItem() instanceof DyeItem) {
                        DyeColor color = ((DyeItem)dyeStack.getItem()).getDyeColor();
                        float[] components = color.getTextureDiffuseColors();
                        int red = (int)(components[0] * 255.0F);
                        int green = (int)(components[1] * 255.0F);
                        int blue = (int)(components[2] * 255.0F);
                        colored.setColor(item, (red & 255) << 16 | (green & 255) << 8 | blue & 255);
                    } else {
                        colored.removeColor(item);
                    }
                } else {
                    colored.removeColor(item);
                }
            }
        }

    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        boolean result = super.mouseClicked(mouseX, mouseY, mouseButton);
        showRemaining = this.checkBoxMaterials.isToggled();

        for(int i = 0; i < this.tabs.size(); ++i) {
            if (RenderUtil.isMouseWithin((int)mouseX, (int)mouseY, this.leftPos + 28 * i, this.topPos - 28, 28, 28)) {
                this.currentTab = this.tabs.get(i);
                this.loadItem(this.currentTab.getCurrentIndex());
                this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            }
        }

        return result;
    }

    private void loadItem(int index) {
        RE8WorkbenchRecipe recipe = this.currentTab.getRecipes().get(index);
        this.displayStack = recipe.getItem().copy();
        this.updateColor();
        this.materials.clear();
        List<RE8WorkbenchIngredient> ingredients = recipe.getMaterials();
        if (ingredients != null) {
            Iterator var4 = ingredients.iterator();

            while(var4.hasNext()) {
                RE8WorkbenchIngredient ingredient = (RE8WorkbenchIngredient)var4.next();
                RE8WorkbenchScreen.MaterialItem item = new RE8WorkbenchScreen.MaterialItem(ingredient);
                item.updateEnabledState();
                this.materials.add(item);
            }

            this.currentTab.setCurrentIndex(index);
        }

    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
        int startX = this.leftPos;
        int startY = this.topPos;

        int i;
        for(i = 0; i < this.tabs.size(); ++i) {
            if (RenderUtil.isMouseWithin(mouseX, mouseY, startX + 28 * i, startY - 28, 28, 28)) {
                this.renderTooltip(poseStack, Component.translatable(((RE8WorkbenchScreen.Tab)this.tabs.get(i)).getTabKey()), mouseX, mouseY);
                return;
            }
        }

        for(i = 0; i < this.filteredMaterials.size(); ++i) {
            int itemX = startX + 172;
            int itemY = startY + i * 19 + 63;
            if (RenderUtil.isMouseWithin(mouseX, mouseY, itemX, itemY, 80, 19)) {
                RE8WorkbenchScreen.MaterialItem materialItem = (RE8WorkbenchScreen.MaterialItem)this.filteredMaterials.get(i);
                if (materialItem != RE8WorkbenchScreen.MaterialItem.EMPTY) {
                    this.renderTooltip(poseStack, materialItem.getDisplayStack(), mouseX, mouseY);
                    return;
                }
            }
        }

        if (RenderUtil.isMouseWithin(mouseX, mouseY, startX + 8, startY + 38, 160, 48)) {
            this.renderTooltip(poseStack, this.displayStack, mouseX, mouseY);
        }

    }

    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        int offset = this.tabs.isEmpty() ? 0 : 28;
        this.font.draw(poseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY - 28.0F + (float)offset, 4210752);
        this.font.draw(poseStack, this.playerInventory.getDisplayName(), (float)this.inventoryLabelX, (float)this.inventoryLabelY - 9.0F + (float)offset, 4210752);
    }

    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        partialTicks = Minecraft.getInstance().getFrameTime();
        int startX = this.leftPos;
        int startY = this.topPos;
        RenderSystem.enableBlend();

        int i;
        for(i = 0; i < this.tabs.size(); ++i) {
            RE8WorkbenchScreen.Tab tab = this.tabs.get(i);
            if (tab != this.currentTab) {
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, GUI_BASE);
                this.blit(poseStack, startX + 28 * i, startY - 28, 80, 184, 28, 32);
                Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(tab.getIcon(), startX + 28 * i + 6, startY - 28 + 8);
                Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(this.font, tab.getIcon(), startX + 28 * i + 6, startY - 28 + 8, null);
            }
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_BASE);
        this.blit(poseStack, startX, startY, 0, 0, 173, 184);
        blit(poseStack, startX + 173, startY, 78, 184, 173.0F, 0.0F, 1, 184, 256, 256);
        this.blit(poseStack, startX + 251, startY, 174, 0, 24, 184);
        this.blit(poseStack, startX + 172, startY + 16, 198, 0, 20, 20);
        if (this.currentTab != null) {
            i = this.tabs.indexOf(this.currentTab);
            int u = i == 0 ? 80 : 108;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, GUI_BASE);
            this.blit(poseStack, startX + 28 * i, startY - 28, u, 214, 28, 32);
            Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(this.currentTab.getIcon(), startX + 28 * i + 6, startY - 28 + 8);
            Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(this.font, this.currentTab.getIcon(), startX + 28 * i + 6, startY - 28 + 8, null);
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_BASE);
        if (this.workbench.getItem(0).isEmpty()) {
            this.blit(poseStack, startX + 174, startY + 18, 165, 199, 16, 16);
        }

        ItemStack currentItem = this.displayStack;
        StringBuilder builder = new StringBuilder(currentItem.getHoverName().getString());
        if (currentItem.getCount() > 1) {
            builder.append(ChatFormatting.GOLD);
            builder.append(ChatFormatting.BOLD);
            builder.append(" x ");
            builder.append(currentItem.getCount());
        }

        drawCenteredString(poseStack, this.font, builder.toString(), startX + 88, startY + 22, Color.WHITE.getRGB());
        GL11.glEnable(3089);
        RenderUtil.scissor(startX + 8, startY + 17, 160, 70);
        PoseStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushPose();
        modelViewStack.translate((double)(startX + 88), (double)(startY + 60), 100.0D);
        modelViewStack.scale(50.0F, -50.0F, 50.0F);
        modelViewStack.mulPose(Vector3f.XP.rotationDegrees(5.0F));
        modelViewStack.mulPose(Vector3f.YP.rotationDegrees((float)Minecraft.getInstance().player.tickCount + partialTicks));
        RenderSystem.applyModelViewMatrix();
        MultiBufferSource.BufferSource buffer = this.minecraft.renderBuffers().bufferSource();
        Minecraft.getInstance().getItemRenderer().render(currentItem, ItemTransforms.TransformType.FIXED, false, poseStack, buffer, 15728880, OverlayTexture.NO_OVERLAY, RenderUtil.getModel(currentItem));
        buffer.endBatch();
        modelViewStack.popPose();
        RenderSystem.applyModelViewMatrix();
        GL11.glDisable(3089);
        this.filteredMaterials = this.getMaterials();

        for(int j = 0; j < this.filteredMaterials.size(); ++j) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, GUI_BASE);
            RE8WorkbenchScreen.MaterialItem materialItem = this.filteredMaterials.get(j);
            ItemStack stack = materialItem.getDisplayStack();
            if (!stack.isEmpty()) {
                Lighting.setupForFlatItems();
                if (materialItem.isEnabled()) {
                    this.blit(poseStack, startX + 172, startY + j * 19 + 63, 0, 184, 80, 19);
                } else {
                    this.blit(poseStack, startX + 172, startY + j * 19 + 63, 0, 222, 80, 19);
                }

                String name = stack.getHoverName().getString();
                if (this.font.width(name) > 55) {
                    String var10000 = this.font.plainSubstrByWidth(name, 50);
                    name = var10000.trim() + "...";
                }

                this.font.draw(poseStack, name, (float)(startX + 172 + 22), (float)(startY + j * 19 + 6 + 63), Color.WHITE.getRGB());
                Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(stack, startX + 172 + 2, startY + j * 19 + 1 + 63);
                if (this.checkBoxMaterials.isToggled()) {
                    int count = InventoryUtil.getItemStackAmount(Minecraft.getInstance().player, stack);
                    stack = stack.copy();
                    stack.setCount(stack.getCount() - count);
                }

                Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(this.font, stack, startX + 172 + 2, startY + j * 19 + 1 + 63, null);
            }
        }

    }

    private List<MaterialItem> getMaterials() {
        List<MaterialItem> materials = NonNullList.withSize(6, MaterialItem.EMPTY);
        List<MaterialItem> filteredMaterials = this.materials.stream().filter((materialItem) -> {
            return this.checkBoxMaterials.isToggled() ? !materialItem.isEnabled() : materialItem != RE8WorkbenchScreen.MaterialItem.EMPTY;
        }).collect(Collectors.toList());

        for(int i = 0; i < filteredMaterials.size() && i < materials.size(); ++i) {
            materials.set(i, filteredMaterials.get(i));
        }

        return materials;
    }

    public List<Tab> getTabs() {
        return ImmutableList.copyOf(this.tabs);
    }

    private static class Tab {
        private final ItemStack icon;
        private final String id;
        private final List<RE8WorkbenchRecipe> items;
        private int currentIndex;

        public Tab(ItemStack icon, String id, List<RE8WorkbenchRecipe> items) {
            this.icon = icon;
            this.id = id;
            this.items = items;
        }

        public ItemStack getIcon() {
            return this.icon;
        }

        public String getTabKey() {
            return "gui.re8gun.re8workbench.tab." + this.id;
        }

        public void setCurrentIndex(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        public int getCurrentIndex() {
            return this.currentIndex;
        }

        public List<RE8WorkbenchRecipe> getRecipes() {
            return this.items;
        }
    }

    public static class MaterialItem {
        public static final RE8WorkbenchScreen.MaterialItem EMPTY = new RE8WorkbenchScreen.MaterialItem();
        private long lastTime = System.currentTimeMillis();
        private int displayIndex;
        private boolean enabled = false;
        private RE8WorkbenchIngredient ingredient;
        private final List<ItemStack> displayStacks = new ArrayList();

        private MaterialItem() {
        }

        private MaterialItem(RE8WorkbenchIngredient ingredient) {
            this.ingredient = ingredient;
            Stream.of(ingredient.getItems()).forEach((stack) -> {
                ItemStack displayStack = stack.copy();
                displayStack.setCount(ingredient.getCount());
                this.displayStacks.add(displayStack);
            });
        }

        public RE8WorkbenchIngredient getIngredient() {
            return this.ingredient;
        }

        public void tick() {
            if (this.ingredient != null) {
                this.updateEnabledState();
                long currentTime = System.currentTimeMillis();
                if (currentTime - this.lastTime >= 1000L) {
                    this.displayIndex = (this.displayIndex + 1) % this.displayStacks.size();
                    this.lastTime = currentTime;
                }

            }
        }

        public ItemStack getDisplayStack() {
            return this.ingredient != null ? this.displayStacks.get(this.displayIndex) : ItemStack.EMPTY;
        }

        public void updateEnabledState() {
            this.enabled = hasWorkstationIngredient(Minecraft.getInstance().player, this.ingredient);
        }

        public boolean isEnabled() {
            return this.ingredient == null || this.enabled;
        }
    }

    public static boolean hasWorkstationIngredient(Player player, RE8WorkbenchIngredient find) {
        int count = 0;
        Iterator var3 = player.getInventory().items.iterator();

        while(var3.hasNext()) {
            ItemStack stack = (ItemStack)var3.next();
            if (!stack.isEmpty() && find.test(stack)) {
                count += stack.getCount();
            }
        }

        return find.getCount() <= count;
    }

}