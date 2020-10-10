/**
 * This file is part of Billund - http://www.computercraft.info/billund
 * Copyright Daniel Ratcliffe, 2013. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.billund.shared.data;

import dan200.billund.shared.registry.BrickHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BillundSet {
    private final String setName;
    private final String setLocal;
    private int cost;
    private ArrayList<ItemStack> brickList;

    public BillundSet(String name, String local, int cost, ArrayList<ItemStack> bricks) {
        this.setName = name;
        this.setLocal = local;
        this.cost = cost;
        this.brickList = bricks;
    }

    public BillundSet(String name, String local, int cost) {
        this.setName = name;
        this.setLocal = local;
        this.cost = cost;
        this.brickList = new ArrayList<>();
    }

    public String getSetName() {
        return setName;
    }

    public String getSetLocal() {
        return setLocal;
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<ItemStack> getBrickList() {
        if(getSetName().equalsIgnoreCase("BlindBag")) {
            ArrayList bricks = new ArrayList();
            for (int i=0; i<27; i++) {
                addRandom(bricks);
            }
            return bricks;
        } else {
            return brickList;
        }
    }

    private void addRandom(ArrayList<ItemStack> bricks) {
        Random random = new Random();
        bricks.add(BrickHelper.createBrickStack(
                random.nextBoolean(),
                random.nextBoolean(),
                random.nextBoolean(),
                DyeColor.values()[random.nextInt(DyeColor.values().length)].getColorValue(),
                random.nextInt(2) + 1,
                random.nextInt(6) + 1,
                24
        ));
    }

    public void populateChest(IInventory inv) {
        ArrayList<ItemStack> bricks = getBrickList();
        for(int i = 0; i < bricks.size(); i++) {
            if(i > inv.getSizeInventory()) break;

            ItemStack stack = bricks.get(i);
            if(!stack.isEmpty()) {
                inv.setInventorySlotContents(i, stack);
            }
        }
    }

    public void addBricks(DyeColor color, int width, int depth, int quantity) {
        this.brickList.add(BrickHelper.createBrickStack(color, width, depth, quantity));
    }

    public void addBricks(List<ItemStack> stackList) {
        this.brickList.addAll(stackList);
    }
}
