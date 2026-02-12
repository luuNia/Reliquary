package org.octave.chestobserver.util;

public class GradientText {

    private static final int[] START = {72, 0, 130};   // #480082
    private static final int[] END   = {180, 70, 255}; // #B446FF

    public static String apply(String text) {
        StringBuilder out = new StringBuilder();
        int length = text.length();

        for (int i = 0; i < length; i++) {
            double ratio = (double) i / (length - 1);

            int r = (int) (START[0] + (END[0] - START[0]) * ratio);
            int g = (int) (START[1] + (END[1] - START[1]) * ratio);
            int b = (int) (START[2] + (END[2] - START[2]) * ratio);

            out.append(toColorCode(r, g, b))
                    .append(text.charAt(i));
        }
        return out.toString();
    }

    private static String toColorCode(int r, int g, int b) {
        return String.format("§x§%X§%X§%X§%X§%X§%X",
                (r >> 4) & 0xF, r & 0xF,
                (g >> 4) & 0xF, g & 0xF,
                (b >> 4) & 0xF, b & 0xF
        );
    }
}
