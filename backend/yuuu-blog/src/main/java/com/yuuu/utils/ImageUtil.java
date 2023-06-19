package com.yuuu.utils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
/**
 * @author: yuuu
 * Description:
 * Email: ***REMOVED***
 * CreatedTime: 2023-06-01 15:01:15:01
 */

public class ImageUtil {
    public static String toBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] bytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }
}
