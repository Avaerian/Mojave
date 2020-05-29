package org.avaeriandev.engine.window;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Input {

    private static Set<Integer> charsDown = new HashSet<>();

    public static boolean isKeyDown(int keycode) {
        return charsDown.contains(keycode);
    }

    public static void addKey(int keycode) {
        charsDown.add(keycode);
    }

    public static void removeKey(int keycode) {
        charsDown.remove(keycode);
    }

    public static List<Character> getHeldKeys() {
        List<Character> keysDown = new ArrayList<>();
        charsDown.forEach(keycode -> keysDown.add((char) (int) keycode));
        return keysDown;
    }

}
