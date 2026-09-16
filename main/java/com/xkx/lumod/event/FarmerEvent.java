package com.xkx.lumod.event;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod("lumod")
@Mod.EventBusSubscriber
public class FarmerEvent {
    @SubscribeEvent
    public static void breakFarmland(BlockEvent.FarmlandTrampleEvent event) { //踩踏耕地
        Entity entity =event.getEntity();
        float height = event.getFallDistance();
        if (entity instanceof Player player) {
            String info = player.getName().getContents();
            player.sendMessage(new TextComponent( info.toString() +  " has fell from a height of" + height), Util.NIL_UUID);
            player.sendMessage(new TextComponent( "Unfortunately, " + info.toString() +  " has tramped a farmland just now"), Util.NIL_UUID);
            player.sendMessage(new TextComponent( "Goodbye! " + info.toString()), Util.NIL_UUID);
            player.kill();
        }
    }

    @SubscribeEvent
    public static void ploughFarmland(PlayerInteractEvent.RightClickBlock event) { //耕种草方块或者泥土方块
        Level world = event.getWorld();
        BlockPos pos = event.getPos();
        Player player = event.getPlayer();
        String info = player.getName().getContents();
        ItemStack heldItemStack = event.getItemStack();
        if (heldItemStack.getItem() instanceof HoeItem && player.level.isClientSide()) { // 判断手持是不是锄头
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT ) {
                player.giveExperiencePoints(1);
                player.sendMessage(new TextComponent("Look! " + info.toString() + " is ploughing!"), Util.NIL_UUID);
                player.sendMessage(new TextComponent("We should learn form him!"), Util.NIL_UUID);
            }
        }
    }


    @SubscribeEvent
    public static void plantEvent(PlayerInteractEvent.RightClickBlock event){  //小麦种植的鼓励
        Player player = event.getPlayer();
        Level level = player.level;
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        String info = player.getName().getContents();
        Item itemHand = event.getItemStack().getItem();
        if (itemHand == Items.WHEAT_SEEDS && state.getBlock() instanceof FarmBlock){
            if(level.isClientSide()){
                player.sendMessage(new TextComponent("Look! " + info.toString() + " is seeding wheat!"), Util.NIL_UUID);
            }
        }
    }


    @SubscribeEvent
    public static void harvestReminder(BlockEvent.CropGrowEvent.Post event){  //成熟提醒
        BlockPos pos = event.getPos();
        BlockState state = event.getWorld().getBlockState(pos);
        Block block = state.getBlock();
        Player player = event.getWorld().getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 100, false); // 搜索半径一百内的玩家
        if(player == null || block != Blocks.WHEAT)   return;
        String info = player.getName().getContents();
        if (block instanceof CropBlock states) {
            IntegerProperty age = states.getAgeProperty();
            int a = state.getValue(age); //作物目前的成熟等级
            if(a >= states.getMaxAge() - 2) {  // 比全熟少一两级也没关系
                player.sendMessage(new TextComponent("Hey! " + info.toString() + ", there's wheat ripening near you"), Util.NIL_UUID);
            }
        }
    }


    @SubscribeEvent
    public static void rubbishThrow(ItemTossEvent event){ //乱扔垃圾检测
        Player player = event.getPlayer();
        Item item = event.getEntityItem().getItem().getItem();
        String info = player.getName().getContents();
        if(item == Items.WHEAT_SEEDS)
            player.sendMessage(new TextComponent( info.toString() + ", don't throw waste!"), Util.NIL_UUID);
    }
}
