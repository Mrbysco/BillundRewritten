package dan200.billund.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import dan200.billund.Billund;
import dan200.billund.shared.data.BillundSet;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class OrderFormScreen extends Screen {
    private static final ResourceLocation background = new ResourceLocation("billund", "textures/gui/orderform.png");

    private static final int xSize = 192;
    private static final int ySize = 215;
    private static final int itemsPerPage = 6;

    private final PlayerEntity player;
    private final List<List<BillundSet>> sets;
    private final List<BillundSet> orders;
    private boolean ordered;
    private int page;

    public OrderFormScreen(PlayerEntity player, List<BillundSet> billundSets) {
        super(new TranslationTextComponent("billund.gui.orderForm.title"));
        this.player = player;
        this.orders = new ArrayList<>();
        this.sets = new ArrayList<>();
        this.page = 0;
        int count = 0;
        List<BillundSet> temp = new ArrayList<>();
        for(BillundSet set : billundSets) {
            count++;
            temp.add(set);
            if (count >= itemsPerPage) {
                count = 0;
                this.sets.add(temp);
                temp = new ArrayList<>();
            }
        }
        if (!temp.isEmpty())
            this.sets.add(temp);
        this.ordered = false;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void tick() {
        // TODO: If the item got lost, close GUI
        super.tick();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        int startY = (height - ySize) / 2;
        int startX = (width - xSize) / 2;
        if (button == 0) {
            int localX = (int)mouseX - startX;
            int localY = (int)mouseY - startY;

            // Test tickboxes
            if (localX >= 160 && localX < 177 && localY >= 33) {
                int tickBoxIndex = (localY - 33) / 23;
                int tickBoxLocalY = (localY - 33) % 23;
                if (tickBoxIndex >= 0 && tickBoxIndex < sets.get(page).size() && tickBoxLocalY < 17) {
                    if (!ordered) {
                        for (int i = 0; i < sets.get(page).size(); ++i) {
                            if (i == tickBoxIndex) {
                                BillundSet set = sets.get(page).get(i);
                                if (orders.contains(set))
                                    orders.remove(set);
                                else
                                    orders.add(set);
                            }
                        }
                    }
                }
            }

            // Test order button
            if (localX >= 102 && localX < 177 && localY >= 173 && localY < 193) {
                if (canPlayerOrder())
                    order();
            }

            // page cycle
            if (this.sets.size() > 1) {
                if (localX >= 12 && localX < 26 && localY >= 149 && localY < 170)
                    this.page = this.page - 1 < 0 ? this.sets.size() - 1 : this.page - 1;
                if (localX >= 90 && localX < 104 && localY >= 149 && localY < 170)
                    this.page = this.page + 2 > this.sets.size() ? 0 : this.page + 1;
            }
        }

        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Draw background
        this.renderBackground(matrixStack);

        // Draw the form
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.minecraft.getTextureManager().bindTexture(background);

        int startY = (height - ySize) / 2;
        int startX = (width - xSize) / 2;
        blit(matrixStack, startX, startY, 0, 0, xSize, ySize);
        blit(matrixStack, startX, startY - 5, 0, ySize, xSize, 5);

        // Draw the ticks
        for (int i = 0; i < sets.get(page).size(); ++i) {
            if (orders.contains(sets.get(page).get(i)))
                blit(matrixStack, startX + 160, startY + 31 + i * 23, xSize, 0, 19, 19);
        }

        // Draw page turners
        if (this.sets.size() > 1) {
            blit(matrixStack, startX + 10, startY + 153, xSize, 23, 14, 14);
            blit(matrixStack, startX + 88, startY + 153, xSize, 37, 14, 14);
        }

        // Draw the text
        font.drawString(matrixStack, I18n.format("billund.gui.orderForm.title"), startX + 8, startY + 10, 0x4c5156);

        String currency = getPlayerBalance() + " / " + getOrderCost();
        int currencyColor = canPlayerAffordOrder() ? 0x4c5156 : 0xae1e22;
        font.drawString(matrixStack, currency, startX + xSize - 25 - font.getStringWidth(currency), startY + 10, currencyColor);

        for (int i = 0; i < sets.get(page).size(); ++i)
            font.drawString(matrixStack, I18n.format(sets.get(page).get(i).getSetLocal()), startX + 16, startY + 38 + i * 23, 0x4c5156);

        String order = I18n.format("billund.gui.orderForm." + (ordered ? "placed" : "place"));
        int color = canPlayerOrder() ? 0x4c5156 : 0xb3a8a7;
        font.drawString(matrixStack, order, (float)(startX + 102 + (75 - font.getStringWidth(order)) / 2), startY + 180, color);

        if (this.sets.size() > 1) {
            String page = I18n.format("billund.gui.orderForm.pages", this.page + 1, this.sets.size());
            font.drawString(matrixStack, page, (float)(startX + 22 + (68 - font.getStringWidth(page)) / 2), startY + 156, 0x4c5156);
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    private boolean canPlayerOrder() {
        if (!ordered) {
            return canPlayerAffordOrder();
        }
        return false;
    }

    private boolean canPlayerAffordOrder() {
        int order = getOrderCost();
        int balance = getPlayerBalance();
        return player.abilities.isCreativeMode || balance >= order;
    }

    private void order() {
        if (!ordered) {
            List<String> ordersList = new ArrayList<>();
            // Send our orders to the server
            for (BillundSet order : orders)
                if (order != null)
                    ordersList.add(order.getSetName());

//            MessageBillundOrder packet = new MessageBillundOrder(ordersList.toArray(new String[ordersList.size()])); TODO: Packet here
//            MessageHandler.INSTANCE.sendToServer(packet);

            Billund.LOGGER.info("Clicked Order");

            // Ensure we don't order again
//            ordered = true; TODO: RE-enable this to make sure we don't order again
        }
    }

    private int getPlayerBalance() {
        int total = 0;
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == Items.EMERALD)
                total += stack.getCount();
        }
        return total;
    }

    private int getOrderCost() {
        int total = 0;
        for (BillundSet set : orders)
            total += set.getCost();
        return total;
    }
}
