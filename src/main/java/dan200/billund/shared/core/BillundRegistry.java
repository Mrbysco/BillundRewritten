package dan200.billund.shared.core;

import dan200.billund.Billund;
import dan200.billund.shared.block.BillundBlock;
import dan200.billund.shared.item.BrickItem;
import dan200.billund.shared.item.OrderFormItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BillundRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Billund.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Billund.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Billund.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Billund.MOD_ID);

    public static final RegistryObject<Item> WHITE_1_1_BRICK = ITEMS.register("white_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.WHITE));
    public static final RegistryObject<Item> WHITE_1_2_BRICK = ITEMS.register("white_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.WHITE));
    public static final RegistryObject<Item> WHITE_1_3_BRICK = ITEMS.register("white_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.WHITE));
    public static final RegistryObject<Item> WHITE_1_4_BRICK = ITEMS.register("white_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.WHITE));
    public static final RegistryObject<Item> WHITE_1_6_BRICK = ITEMS.register("white_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.WHITE));
    public static final RegistryObject<Item> WHITE_2_2_BRICK = ITEMS.register("white_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.WHITE));
    public static final RegistryObject<Item> WHITE_2_3_BRICK = ITEMS.register("white_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.WHITE));
    public static final RegistryObject<Item> WHITE_2_4_BRICK = ITEMS.register("white_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.WHITE));
    public static final RegistryObject<Item> WHITE_2_6_BRICK = ITEMS.register("white_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.WHITE));

    public static final RegistryObject<Item> ORANGE_1_1_BRICK = ITEMS.register("orange_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.ORANGE));
    public static final RegistryObject<Item> ORANGE_1_2_BRICK = ITEMS.register("orange_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.ORANGE));
    public static final RegistryObject<Item> ORANGE_1_3_BRICK = ITEMS.register("orange_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.ORANGE));
    public static final RegistryObject<Item> ORANGE_1_4_BRICK = ITEMS.register("orange_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.ORANGE));
    public static final RegistryObject<Item> ORANGE_1_6_BRICK = ITEMS.register("orange_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.ORANGE));
    public static final RegistryObject<Item> ORANGE_2_2_BRICK = ITEMS.register("orange_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.ORANGE));
    public static final RegistryObject<Item> ORANGE_2_3_BRICK = ITEMS.register("orange_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.ORANGE));
    public static final RegistryObject<Item> ORANGE_2_4_BRICK = ITEMS.register("orange_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.ORANGE));
    public static final RegistryObject<Item> ORANGE_2_6_BRICK = ITEMS.register("orange_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.ORANGE));

    public static final RegistryObject<Item> MAGENTA_1_1_BRICK = ITEMS.register("magenta_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.MAGENTA));
    public static final RegistryObject<Item> MAGENTA_1_2_BRICK = ITEMS.register("magenta_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.MAGENTA));
    public static final RegistryObject<Item> MAGENTA_1_3_BRICK = ITEMS.register("magenta_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.MAGENTA));
    public static final RegistryObject<Item> MAGENTA_1_4_BRICK = ITEMS.register("magenta_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.MAGENTA));
    public static final RegistryObject<Item> MAGENTA_1_6_BRICK = ITEMS.register("magenta_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.MAGENTA));
    public static final RegistryObject<Item> MAGENTA_2_2_BRICK = ITEMS.register("magenta_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.MAGENTA));
    public static final RegistryObject<Item> MAGENTA_2_3_BRICK = ITEMS.register("magenta_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.MAGENTA));
    public static final RegistryObject<Item> MAGENTA_2_4_BRICK = ITEMS.register("magenta_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.MAGENTA));
    public static final RegistryObject<Item> MAGENTA_2_6_BRICK = ITEMS.register("magenta_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.MAGENTA));

    public static final RegistryObject<Item> LIGHT_BLUE_1_1_BRICK = ITEMS.register("light_blue_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.LIGHT_BLUE));
    public static final RegistryObject<Item> LIGHT_BLUE_1_2_BRICK = ITEMS.register("light_blue_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.LIGHT_BLUE));
    public static final RegistryObject<Item> LIGHT_BLUE_1_3_BRICK = ITEMS.register("light_blue_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.LIGHT_BLUE));
    public static final RegistryObject<Item> LIGHT_BLUE_1_4_BRICK = ITEMS.register("light_blue_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.LIGHT_BLUE));
    public static final RegistryObject<Item> LIGHT_BLUE_1_6_BRICK = ITEMS.register("light_blue_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.LIGHT_BLUE));
    public static final RegistryObject<Item> LIGHT_BLUE_2_2_BRICK = ITEMS.register("light_blue_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.LIGHT_BLUE));
    public static final RegistryObject<Item> LIGHT_BLUE_2_3_BRICK = ITEMS.register("light_blue_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.LIGHT_BLUE));
    public static final RegistryObject<Item> LIGHT_BLUE_2_4_BRICK = ITEMS.register("light_blue_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.LIGHT_BLUE));
    public static final RegistryObject<Item> LIGHT_BLUE_2_6_BRICK = ITEMS.register("light_blue_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.LIGHT_BLUE));

    public static final RegistryObject<Item> YELLOW_1_1_BRICK = ITEMS.register("yellow_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.YELLOW));
    public static final RegistryObject<Item> YELLOW_1_2_BRICK = ITEMS.register("yellow_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.YELLOW));
    public static final RegistryObject<Item> YELLOW_1_3_BRICK = ITEMS.register("yellow_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.YELLOW));
    public static final RegistryObject<Item> YELLOW_1_4_BRICK = ITEMS.register("yellow_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.YELLOW));
    public static final RegistryObject<Item> YELLOW_1_6_BRICK = ITEMS.register("yellow_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.YELLOW));
    public static final RegistryObject<Item> YELLOW_2_2_BRICK = ITEMS.register("yellow_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.YELLOW));
    public static final RegistryObject<Item> YELLOW_2_3_BRICK = ITEMS.register("yellow_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.YELLOW));
    public static final RegistryObject<Item> YELLOW_2_4_BRICK = ITEMS.register("yellow_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.YELLOW));
    public static final RegistryObject<Item> YELLOW_2_6_BRICK = ITEMS.register("yellow_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.YELLOW));

    public static final RegistryObject<Item> LIME_1_1_BRICK = ITEMS.register("lime_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.LIME));
    public static final RegistryObject<Item> LIME_1_2_BRICK = ITEMS.register("lime_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.LIME));
    public static final RegistryObject<Item> LIME_1_3_BRICK = ITEMS.register("lime_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.LIME));
    public static final RegistryObject<Item> LIME_1_4_BRICK = ITEMS.register("lime_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.LIME));
    public static final RegistryObject<Item> LIME_1_6_BRICK = ITEMS.register("lime_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.LIME));
    public static final RegistryObject<Item> LIME_2_2_BRICK = ITEMS.register("lime_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.LIME));
    public static final RegistryObject<Item> LIME_2_3_BRICK = ITEMS.register("lime_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.LIME));
    public static final RegistryObject<Item> LIME_2_4_BRICK = ITEMS.register("lime_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.LIME));
    public static final RegistryObject<Item> LIME_2_6_BRICK = ITEMS.register("lime_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.LIME));

    public static final RegistryObject<Item> PINK_1_1_BRICK = ITEMS.register("pink_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.PINK));
    public static final RegistryObject<Item> PINK_1_2_BRICK = ITEMS.register("pink_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.PINK));
    public static final RegistryObject<Item> PINK_1_3_BRICK = ITEMS.register("pink_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.PINK));
    public static final RegistryObject<Item> PINK_1_4_BRICK = ITEMS.register("pink_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.PINK));
    public static final RegistryObject<Item> PINK_1_6_BRICK = ITEMS.register("pink_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.PINK));
    public static final RegistryObject<Item> PINK_2_2_BRICK = ITEMS.register("pink_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.PINK));
    public static final RegistryObject<Item> PINK_2_3_BRICK = ITEMS.register("pink_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.PINK));
    public static final RegistryObject<Item> PINK_2_4_BRICK = ITEMS.register("pink_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.PINK));
    public static final RegistryObject<Item> PINK_2_6_BRICK = ITEMS.register("pink_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.PINK));

    public static final RegistryObject<Item> GRAY_1_1_BRICK = ITEMS.register("gray_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.GRAY));
    public static final RegistryObject<Item> GRAY_1_2_BRICK = ITEMS.register("gray_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.GRAY));
    public static final RegistryObject<Item> GRAY_1_3_BRICK = ITEMS.register("gray_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.GRAY));
    public static final RegistryObject<Item> GRAY_1_4_BRICK = ITEMS.register("gray_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.GRAY));
    public static final RegistryObject<Item> GRAY_1_6_BRICK = ITEMS.register("gray_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.GRAY));
    public static final RegistryObject<Item> GRAY_2_2_BRICK = ITEMS.register("gray_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.GRAY));
    public static final RegistryObject<Item> GRAY_2_3_BRICK = ITEMS.register("gray_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.GRAY));
    public static final RegistryObject<Item> GRAY_2_4_BRICK = ITEMS.register("gray_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.GRAY));
    public static final RegistryObject<Item> GRAY_2_6_BRICK = ITEMS.register("gray_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.GRAY));

    public static final RegistryObject<Item> LIGHT_GRAY_1_1_BRICK = ITEMS.register("light_gray_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.LIGHT_GRAY));
    public static final RegistryObject<Item> LIGHT_GRAY_1_2_BRICK = ITEMS.register("light_gray_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.LIGHT_GRAY));
    public static final RegistryObject<Item> LIGHT_GRAY_1_3_BRICK = ITEMS.register("light_gray_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.LIGHT_GRAY));
    public static final RegistryObject<Item> LIGHT_GRAY_1_4_BRICK = ITEMS.register("light_gray_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.LIGHT_GRAY));
    public static final RegistryObject<Item> LIGHT_GRAY_1_6_BRICK = ITEMS.register("light_gray_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.LIGHT_GRAY));
    public static final RegistryObject<Item> LIGHT_GRAY_2_2_BRICK = ITEMS.register("light_gray_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.LIGHT_GRAY));
    public static final RegistryObject<Item> LIGHT_GRAY_2_3_BRICK = ITEMS.register("light_gray_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.LIGHT_GRAY));
    public static final RegistryObject<Item> LIGHT_GRAY_2_4_BRICK = ITEMS.register("light_gray_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.LIGHT_GRAY));
    public static final RegistryObject<Item> LIGHT_GRAY_2_6_BRICK = ITEMS.register("light_gray_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.LIGHT_GRAY));

    public static final RegistryObject<Item> CYAN_1_1_BRICK = ITEMS.register("cyan_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.CYAN));
    public static final RegistryObject<Item> CYAN_1_2_BRICK = ITEMS.register("cyan_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.CYAN));
    public static final RegistryObject<Item> CYAN_1_3_BRICK = ITEMS.register("cyan_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.CYAN));
    public static final RegistryObject<Item> CYAN_1_4_BRICK = ITEMS.register("cyan_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.CYAN));
    public static final RegistryObject<Item> CYAN_1_6_BRICK = ITEMS.register("cyan_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.CYAN));
    public static final RegistryObject<Item> CYAN_2_2_BRICK = ITEMS.register("cyan_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.CYAN));
    public static final RegistryObject<Item> CYAN_2_3_BRICK = ITEMS.register("cyan_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.CYAN));
    public static final RegistryObject<Item> CYAN_2_4_BRICK = ITEMS.register("cyan_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.CYAN));
    public static final RegistryObject<Item> CYAN_2_6_BRICK = ITEMS.register("cyan_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.CYAN));

    public static final RegistryObject<Item> PURPLE_1_1_BRICK = ITEMS.register("purple_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.PURPLE));
    public static final RegistryObject<Item> PURPLE_1_2_BRICK = ITEMS.register("purple_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.PURPLE));
    public static final RegistryObject<Item> PURPLE_1_3_BRICK = ITEMS.register("purple_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.PURPLE));
    public static final RegistryObject<Item> PURPLE_1_4_BRICK = ITEMS.register("purple_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.PURPLE));
    public static final RegistryObject<Item> PURPLE_1_6_BRICK = ITEMS.register("purple_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.PURPLE));
    public static final RegistryObject<Item> PURPLE_2_2_BRICK = ITEMS.register("purple_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.PURPLE));
    public static final RegistryObject<Item> PURPLE_2_3_BRICK = ITEMS.register("purple_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.PURPLE));
    public static final RegistryObject<Item> PURPLE_2_4_BRICK = ITEMS.register("purple_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.PURPLE));
    public static final RegistryObject<Item> PURPLE_2_6_BRICK = ITEMS.register("purple_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.PURPLE));

    public static final RegistryObject<Item> BLUE_1_1_BRICK = ITEMS.register("blue_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.BLUE));
    public static final RegistryObject<Item> BLUE_1_2_BRICK = ITEMS.register("blue_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.BLUE));
    public static final RegistryObject<Item> BLUE_1_3_BRICK = ITEMS.register("blue_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.BLUE));
    public static final RegistryObject<Item> BLUE_1_4_BRICK = ITEMS.register("blue_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.BLUE));
    public static final RegistryObject<Item> BLUE_1_6_BRICK = ITEMS.register("blue_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.BLUE));
    public static final RegistryObject<Item> BLUE_2_2_BRICK = ITEMS.register("blue_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.BLUE));
    public static final RegistryObject<Item> BLUE_2_3_BRICK = ITEMS.register("blue_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.BLUE));
    public static final RegistryObject<Item> BLUE_2_4_BRICK = ITEMS.register("blue_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.BLUE));
    public static final RegistryObject<Item> BLUE_2_6_BRICK = ITEMS.register("blue_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.BLUE));

    public static final RegistryObject<Item> BROWN_1_1_BRICK = ITEMS.register("brown_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.BROWN));
    public static final RegistryObject<Item> BROWN_1_2_BRICK = ITEMS.register("brown_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.BROWN));
    public static final RegistryObject<Item> BROWN_1_3_BRICK = ITEMS.register("brown_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.BROWN));
    public static final RegistryObject<Item> BROWN_1_4_BRICK = ITEMS.register("brown_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.BROWN));
    public static final RegistryObject<Item> BROWN_1_6_BRICK = ITEMS.register("brown_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.BROWN));
    public static final RegistryObject<Item> BROWN_2_2_BRICK = ITEMS.register("brown_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.BROWN));
    public static final RegistryObject<Item> BROWN_2_3_BRICK = ITEMS.register("brown_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.BROWN));
    public static final RegistryObject<Item> BROWN_2_4_BRICK = ITEMS.register("brown_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.BROWN));
    public static final RegistryObject<Item> BROWN_2_6_BRICK = ITEMS.register("brown_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.BROWN));

    public static final RegistryObject<Item> GREEN_1_1_BRICK = ITEMS.register("green_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.GREEN));
    public static final RegistryObject<Item> GREEN_1_2_BRICK = ITEMS.register("green_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.GREEN));
    public static final RegistryObject<Item> GREEN_1_3_BRICK = ITEMS.register("green_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.GREEN));
    public static final RegistryObject<Item> GREEN_1_4_BRICK = ITEMS.register("green_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.GREEN));
    public static final RegistryObject<Item> GREEN_1_6_BRICK = ITEMS.register("green_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.GREEN));
    public static final RegistryObject<Item> GREEN_2_2_BRICK = ITEMS.register("green_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.GREEN));
    public static final RegistryObject<Item> GREEN_2_3_BRICK = ITEMS.register("green_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.GREEN));
    public static final RegistryObject<Item> GREEN_2_4_BRICK = ITEMS.register("green_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.GREEN));
    public static final RegistryObject<Item> GREEN_2_6_BRICK = ITEMS.register("green_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.GREEN));

    public static final RegistryObject<Item> RED_1_1_BRICK = ITEMS.register("red_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.RED));
    public static final RegistryObject<Item> RED_1_2_BRICK = ITEMS.register("red_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.RED));
    public static final RegistryObject<Item> RED_1_3_BRICK = ITEMS.register("red_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.RED));
    public static final RegistryObject<Item> RED_1_4_BRICK = ITEMS.register("red_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.RED));
    public static final RegistryObject<Item> RED_1_6_BRICK = ITEMS.register("red_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.RED));
    public static final RegistryObject<Item> RED_2_2_BRICK = ITEMS.register("red_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.RED));
    public static final RegistryObject<Item> RED_2_3_BRICK = ITEMS.register("red_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.RED));
    public static final RegistryObject<Item> RED_2_4_BRICK = ITEMS.register("red_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.RED));
    public static final RegistryObject<Item> RED_2_6_BRICK = ITEMS.register("red_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.RED));

    public static final RegistryObject<Item> BLACK_1_1_BRICK = ITEMS.register("black_1_1_brick" , () -> new BrickItem(new Item.Properties(), 1, 1, DyeColor.BLACK));
    public static final RegistryObject<Item> BLACK_1_2_BRICK = ITEMS.register("black_1_2_brick" , () -> new BrickItem(new Item.Properties(), 1, 2, DyeColor.BLACK));
    public static final RegistryObject<Item> BLACK_1_3_BRICK = ITEMS.register("black_1_3_brick" , () -> new BrickItem(new Item.Properties(), 1, 3, DyeColor.BLACK));
    public static final RegistryObject<Item> BLACK_1_4_BRICK = ITEMS.register("black_1_4_brick" , () -> new BrickItem(new Item.Properties(), 1, 4, DyeColor.BLACK));
    public static final RegistryObject<Item> BLACK_1_6_BRICK = ITEMS.register("black_1_6_brick" , () -> new BrickItem(new Item.Properties(), 1, 6, DyeColor.BLACK));
    public static final RegistryObject<Item> BLACK_2_2_BRICK = ITEMS.register("black_2_2_brick" , () -> new BrickItem(new Item.Properties(), 2, 2, DyeColor.BLACK));
    public static final RegistryObject<Item> BLACK_2_3_BRICK = ITEMS.register("black_2_3_brick" , () -> new BrickItem(new Item.Properties(), 2, 3, DyeColor.BLACK));
    public static final RegistryObject<Item> BLACK_2_4_BRICK = ITEMS.register("black_2_4_brick" , () -> new BrickItem(new Item.Properties(), 2, 4, DyeColor.BLACK));
    public static final RegistryObject<Item> BLACK_2_6_BRICK = ITEMS.register("black_2_6_brick" , () -> new BrickItem(new Item.Properties(), 2, 6, DyeColor.BLACK));

    public static final RegistryObject<Item> ORDER_FORM = ITEMS.register("order_form" , () -> new OrderFormItem(new Item.Properties()));

    public static final RegistryObject<Block> BILLUND = BLOCKS.register("billund", () -> new BillundBlock(Block.Properties.create(Material.WOOD)));

}