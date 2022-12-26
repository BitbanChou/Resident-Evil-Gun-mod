package com.joy187.re8gun.block;

import com.mrcrayfish.guns.block.RotatedObjectBlock;
import com.mrcrayfish.guns.blockentity.WorkbenchBlockEntity;
import com.mrcrayfish.guns.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;


import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class RE8WorkbenchBlock extends RotatedObjectBlock implements EntityBlock
{
    private final Map<BlockState, VoxelShape> SHAPES = new HashMap<>();

    public RE8WorkbenchBlock(Block.Properties properties)
    {
        super(properties);
    }

    private VoxelShape getShape(BlockState state) {
        if (this.SHAPES.containsKey(state)) {
            return this.SHAPES.get(state);
        } else {
            Direction direction = (Direction)state.getValue(FACING);
            List<VoxelShape> shapes = new ArrayList();
            shapes.add(Block.box(0.5D, 0.0D, 0.5D, 15.5D, 13.0D, 15.5D));
            shapes.add(Block.box(0.0D, 13.0D, 0.0D, 16.0D, 15.0D, 16.0D));
            shapes.add(VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0.0D, 15.0D, 0.0D, 4.0D, 16.0D, 2.0D), Direction.SOUTH))[direction.get2DDataValue()]);
            VoxelShape shape = VoxelShapeHelper.combineAll(shapes);
            this.SHAPES.put(state, shape);
            return shape;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
    {
        return this.getShape(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos) {
        return this.getShape(state);
    }

//    @Override
//    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
//    {
//        if(!world.isRemote())
//        {
//            TileEntity tileEntity = world.getTileEntity(pos);
//            if(tileEntity instanceof INamedContainerProvider)
//            {
//                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, (INamedContainerProvider) tileEntity, pos);
//            }
//        }
//        return ActionResultType.SUCCESS;
//    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult result) {
//        if (!world.isClientSide()) {
//            BlockEntity tileEntity = world.getBlockEntity(pos);
//            if (tileEntity instanceof MenuProvider) {
//                NetworkHooks.openScreen((ServerPlayer)playerEntity, (MenuProvider)tileEntity, pos);
//            }
//        }
//
//        return InteractionResult.SUCCESS;
        //System.out.println("1");
        if(!world.isClientSide())
        {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof MenuProvider)
            {
                NetworkHooks.openScreen((ServerPlayer) playerEntity, (MenuProvider) tileEntity, pos);
                //System.out.println("2");
            }
        }
        return InteractionResult.SUCCESS;
    }


    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RE8WorkbenchBlockEntity(pos, state);
    }
}
