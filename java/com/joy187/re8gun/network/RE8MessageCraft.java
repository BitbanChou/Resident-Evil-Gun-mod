package com.joy187.re8gun.network;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.mrcrayfish.guns.common.network.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


public class RE8MessageCraft extends PlayMessage<RE8MessageCraft> {
    private ResourceLocation id;
    private BlockPos pos;

    public RE8MessageCraft() {
    }

    public RE8MessageCraft(ResourceLocation id, BlockPos pos) {
        this.id = id;
        this.pos = pos;
    }

    public void encode(RE8MessageCraft message, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(message.id);
        buffer.writeBlockPos(message.pos);
    }

    public RE8MessageCraft decode(FriendlyByteBuf buffer) {
        return new RE8MessageCraft(buffer.readResourceLocation(), buffer.readBlockPos());
    }

    public void handle(RE8MessageCraft message, Supplier<NetworkEvent.Context> supplier) {
        (supplier.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)supplier.get()).getSender();
            if (player != null) {
                RE8ServerPlayHandler.handleCraft(player, message.id, message.pos);
            }

        });
        (supplier.get()).setPacketHandled(true);
    }
}
