package com.xkx.lumod.trick;

import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.sounds.SoundEvents.CREEPER_PRIMED;

@Mod("lumod")
@Mod.EventBusSubscriber
public class TrickEvents {
    @SubscribeEvent
    public static void bombBomb(PlaySoundAtEntityEvent event){  // 修改了实体的操作声音都为苦力怕的嘶嘶声
        Entity entity = event.getEntity();
        if(entity instanceof LivingEntity)
            event.setSound(CREEPER_PRIMED);
    }

    @SubscribeEvent
    public static void reproduce(BabyEntitySpawnEvent event){ //繁衍生出苦力怕？？
        if (event.getParentA() instanceof Wolf)
            event.setChild(EntityType.CHICKEN.create(event.getChild().level));
    }
}
