package dan200.billund.client;

import dan200.billund.Billund;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static KeyBinding KEY_ROTATE = new KeyBinding(getKey("rotate"), GLFW.GLFW_KEY_R, getKey("category"));

    private static String getKey(String name) {
        return String.join(".", "key", Billund.MOD_ID, name);
    }
}
