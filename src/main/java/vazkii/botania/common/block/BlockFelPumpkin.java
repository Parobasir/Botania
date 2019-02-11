/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Jul 7, 2015, 7:59:10 PM (GMT)]
 */
package vazkii.botania.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.client.core.handler.ModelHandler;
import vazkii.botania.common.lexicon.LexiconData;
import vazkii.botania.common.lib.LibBlockNames;
import vazkii.botania.common.lib.LibMisc;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class BlockFelPumpkin extends BlockMod implements ILexiconable {
	private static final ResourceLocation LOOT_TABLE = new ResourceLocation(LibMisc.MOD_ID, "fel_blaze");
	private static final String TAG_FEL_SPAWNED = "Botania-FelSpawned";

	public BlockFelPumpkin(Builder builder) {
		super(builder);
		setDefaultState(stateContainer.getBaseState().with(BlockStateProperties.HORIZONTAL_FACING, EnumFacing.SOUTH));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING);
	}

	@Override
	public void onBlockAdded(IBlockState state, World world, BlockPos pos, IBlockState oldState) {
		super.onBlockAdded(state, world, pos, oldState);

		if(!world.isRemote && world.getBlockState(pos.down()).getBlock() == Blocks.IRON_BARS && world.getBlockState(pos.down(2)).getBlock() == Blocks.IRON_BARS) {
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			world.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
			world.setBlockState(pos.down(2), Blocks.AIR.getDefaultState());
			EntityBlaze blaze = new EntityBlaze(world);
			blaze.setLocationAndAngles(pos.getX() + 0.5D, pos.getY() - 1.95D, pos.getZ() + 0.5D, 0.0F, 0.0F);
			blaze.deathLootTable = LOOT_TABLE;
			blaze.onInitialSpawn(world.getDifficultyForLocation(pos), null, null);
			world.spawnEntity(blaze);
		}
	}

	@Override
	public IBlockState getStateForPlacement(BlockItemUseContext context) {
		return getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, context.getNearestLookingDirection().getOpposite());
	}

	@Override
	public LexiconEntry getEntry(World world, BlockPos pos, EntityPlayer player, ItemStack lexicon) {
		return LexiconData.gardenOfGlass;
	}

}
