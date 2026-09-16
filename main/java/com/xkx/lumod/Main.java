package com.xkx.lumod;


import com.xkx.lumod.item.LubinItem;
import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("lumod")
@Mod.EventBusSubscriber
public class Main {
    public static final String MOD_ID = "lumod";

    private static final Logger LOGGER = LogManager.getLogger();

    public Main(){

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        LubinItem.register(eventBus); //注册那个物体？

        eventBus.addListener(this::loadMod); //附在这个事件上？

        MinecraftForge.EVENT_BUS.register(this);
    }


    public void loadMod(final FMLCommonSetupEvent event){  //mod被加载时触发该事件，在两个端均有效
        // 当Mod被加载时，可以使用该事件来执行一些在其余Mod装载之前需要完成的操作，
        // 例如注册方块、物品、实体或TileEntity等。此事件还用于设置网络通信和其他在单元测试中不必要的内容。
        LOGGER.info("HELLO FROM LUMOD!");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }


    public static void reSetInventory(Player player){  //初始化物品栏
        Container a = player.getInventory(); //获取物品栏
        a.clearContent(); // 清空玩家物品栏
        ItemStack hoe = new ItemStack(Items.DIAMOND_HOE);  // 设定玩家初始物品
        ItemStack seed = new ItemStack(Items.WHEAT_SEEDS, 64);
        ItemStack boneMeal = new ItemStack(Items.BONE_MEAL, 64);
        ItemStack sword = new ItemStack(Items.WOODEN_SWORD);
        a.setItem(0, hoe); //把指定位置换为该物体
        a.setItem(1, seed);
        a.setItem(2, boneMeal);
        a.setItem(3, sword);
        player.sendMessage(new TextComponent("Your inventory has been initialized successfully!"), Util.NIL_UUID);  // 发送消息告知玩家已经设定完毕
    }

    @SubscribeEvent
    public static void joinIn(PlayerEvent.PlayerLoggedInEvent event) {  //登录的初始化
        Player player =event.getPlayer();
        String info = player.getName().getContents();
        Level level = player.level;
        player.sendMessage(new TextComponent("You you you, this not is " +  info.toString() + " horse? " + "some day not see, too la!"), Util.NIL_UUID);
        player.sendMessage(new TextComponent("This message is send by" + (level.isClientSide()? "CLIENT" : "SERVER")), Util.NIL_UUID);
        player.setHealth(20); // 设置玩家血量
        Container a = player.getInventory(); //获取物品栏
        if(a.isEmpty()){ //物品栏中原有的东西不清除
            reSetInventory(player);
        }
    }

    @SubscribeEvent
    public static void rightClickItem(PlayerInteractEvent.RightClickItem event){  //持剑右击传送
        Player player = event.getPlayer();
        Level level = player.level;
        if (!level.isClientSide()){
            ItemStack ItemStack = event.getItemStack();
            Item item = ItemStack.getItem();
            if (item instanceof SwordItem){
                HitResult hitResult = player.pick(20, 0,false);//获取玩家焦点信息
                Vec3 vec3 = hitResult.getLocation();
                player.teleportTo(vec3.x, vec3.y, vec3.z);
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        // 判断是否为玩家
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            // 取消掉落物品
            event.setCanceled(true);
        }
    }


}
