package com.ordana.immersive_weathering.registry.blocks;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Degradable;

import java.util.Optional;
import java.util.function.Supplier;

public interface Crackable extends Degradable<Crackable.CrackLevel> {
    public static final Supplier<BiMap<Block, Block>> CRACK_LEVEL_INCREASES = Suppliers.memoize(() -> ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder)
            ImmutableBiMap.builder()
            .put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS))
            .put(Blocks.BRICKS, ModBlocks.CRACKED_BRICKS))
            .put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS))
            .put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS))
            .put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS))
            .put(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES))
            .build());

    public static final Supplier<BiMap<Block, Block>> CRACK_LEVEL_DECREASES = Suppliers.memoize(() -> CRACK_LEVEL_INCREASES.get().inverse());

    public static Optional<Block> getDecreasedCrackBlock(Block block) {
        return Optional.ofNullable((Block)CRACK_LEVEL_DECREASES.get().get(block));
    }

    public static Block getUncrackedCrackBlock(Block block) {
        Block block2 = block;
        Block block3 = (Block)CRACK_LEVEL_DECREASES.get().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = (Block)CRACK_LEVEL_DECREASES.get().get(block2);
        }
        return block2;
    }

    public static Optional<BlockState> getDecreasedCrackState(BlockState state) {
        return Crackable.getDecreasedCrackBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    public static Optional<Block> getIncreasedCrackBlock(Block block) {
        return Optional.ofNullable((Block)CRACK_LEVEL_INCREASES.get().get(block));
    }

    public static BlockState getUncrackedCrackState(BlockState state) {
        return Crackable.getUncrackedCrackBlock(state.getBlock()).getStateWithProperties(state);
    }

    @Override
    default public Optional<BlockState> getDegradationResult(BlockState state) {
        return Crackable.getIncreasedCrackBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    default public float getDegradationChanceMultiplier() {
        return 1.0f;
    }

    public static enum CrackLevel {
        UNCRACKED,
        CRACKED;

    }
}
