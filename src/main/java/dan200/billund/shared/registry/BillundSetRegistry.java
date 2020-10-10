package dan200.billund.shared.registry;

import dan200.billund.shared.data.BillundSet;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BillundSetRegistry {
    private static BillundSetRegistry instance;
    private Map<String, BillundSet> billundSets;

    public static BillundSetRegistry instance() {
        if (instance == null)
            instance = new BillundSetRegistry();
        return instance;
    }

    private BillundSetRegistry() {
        this.billundSets = new LinkedHashMap<>();
    }

    public void addSet(BillundSet set) {
        this.billundSets.put(set.getSetName(), set);
    }

    public BillundSet getBillundSet(String setName) {
        return billundSets.containsKey(setName) ? this.billundSets.get(setName) : null;
    }

    public List<BillundSet> getAllSets() {
        return new ArrayList<>(this.billundSets.values());
    }

    public void initializeDefaults() {
        instance();

        instance.addSet(new BillundSet("StarterSet", "billund.pack.starter", 7, getStarterBricks()));
        instance.addSet(new BillundSet("ColorPackA", "billund.pack.colorA", 10, getStarterBricks()));
        instance.addSet(new BillundSet("ColorPackB", "billund.pack.colorB", 10, getStarterBricks()));
        instance.addSet(new BillundSet("ColorPackC", "billund.pack.colorC", 10, getStarterBricks()));
        instance.addSet(new BillundSet("ColorPackD", "billund.pack.colorD", 10, getStarterBricks()));
        instance.addSet(new BillundSet("BlindBag", "billund.pack.random", 15));
    }

    private ArrayList<ItemStack> getStarterBricks() {
        ArrayList<ItemStack> bricks = new ArrayList<>();
        addBasic(bricks, DyeColor.RED);
        addBasic(bricks, DyeColor.GREEN);
        addBasic(bricks, DyeColor.BLUE);
        addBasic(bricks, DyeColor.YELLOW);
        addBasic(bricks, DyeColor.WHITE);
        addBasic(bricks, DyeColor.BLACK);
        return bricks;
    }

    private ArrayList<ItemStack> getColorPackABricks() {
        ArrayList<ItemStack> bricks = new ArrayList<>();
        addAll(bricks, DyeColor.RED);
        addAll(bricks, DyeColor.GREEN);
        addAll(bricks, DyeColor.BLUE);
        return bricks;
    }

    private ArrayList<ItemStack> getColorPackBBricks() {
        ArrayList<ItemStack> bricks = new ArrayList<>();
        addAll(bricks, DyeColor.ORANGE);
        addAll(bricks, DyeColor.YELLOW);
        addAll(bricks, DyeColor.LIME);
        return bricks;
    }

    private ArrayList<ItemStack> getColorPackCBricks() {
        ArrayList<ItemStack> bricks = new ArrayList<>();
        addAll(bricks, DyeColor.PINK);
        addAll(bricks, DyeColor.PURPLE);
        addAll(bricks, DyeColor.WHITE);
        return bricks;
    }

    private ArrayList<ItemStack> getColorPackDBricks() {
        ArrayList<ItemStack> bricks = new ArrayList<>();
        addAll(bricks, DyeColor.LIGHT_GRAY);
        addAll(bricks, DyeColor.GRAY);
        addAll(bricks, DyeColor.BLACK);
        return bricks;
    }

    private void addBasic(ArrayList<ItemStack> bricks, DyeColor color) {
        bricks.add(BrickHelper.createBrickStack(color, 1, 2, 24));
        bricks.add(BrickHelper.createBrickStack(color, 1, 4, 24));
        bricks.add(BrickHelper.createBrickStack(color, 2, 2, 24));
        bricks.add(BrickHelper.createBrickStack(color, 2, 4, 24));
    }

    private void addAll(ArrayList<ItemStack> bricks, DyeColor color) {
        bricks.add(BrickHelper.createBrickStack(color, 1, 1, 24));
        bricks.add(BrickHelper.createBrickStack(color, 1, 2, 24));
        bricks.add(BrickHelper.createBrickStack(color, 1, 3, 24));
        bricks.add(BrickHelper.createBrickStack(color, 1, 4, 24));
        bricks.add(BrickHelper.createBrickStack(color, 1, 6, 24));
        bricks.add(BrickHelper.createBrickStack(color, 2, 2, 24));
        bricks.add(BrickHelper.createBrickStack(color, 2, 3, 24));
        bricks.add(BrickHelper.createBrickStack(color, 2, 4, 24));
        bricks.add(BrickHelper.createBrickStack(color, 2, 6, 24));
    }
}
