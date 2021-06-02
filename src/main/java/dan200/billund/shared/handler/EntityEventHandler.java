package dan200.billund.shared.handler;

import dan200.billund.shared.block.BillundBlock;
import dan200.billund.shared.item.BrickItem;
import dan200.billund.shared.registry.BillundRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntityEventHandler {
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			// See what brick is in front of the player
			PlayerEntity player = event.player;
			World world = player.world;
			if (world != null) {
				BillundBlock.setHoverBrick(BrickItem.getExistingBrick(world, player, 1F));
			} else {
				BillundBlock.setHoverBrick(null);
			}
		}
	}

	@SubscribeEvent
	public void onEntityLivingDeath(LivingDropsEvent event) {
		if(!event.getEntity().world.isRemote) {
			Entity entity = event.getEntity();
			if(entity instanceof ZombieEntity) {
				MobEntity mobEntity = (MobEntity) entity;
				if ((mobEntity.isChild() && mobEntity.getRNG().nextInt(20) == 0) || (!mobEntity.isChild() && mobEntity.getRNG().nextInt(100) == 0)) {
					mobEntity.entityDropItem(new ItemStack(BillundRegistry.ORDER_FORM.get()), 0.0f);
				}
			}
		}
	}
}
