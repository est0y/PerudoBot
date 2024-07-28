package ru.est0y.perudo.services.messaging.images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiceImageCreator {

    private File createImageFile(List<Integer> dice, String packName) throws InterruptedException {
        try {
            Supplier<Stream<BufferedImage>> images = () -> dice.stream().map(die -> DiceImageCreator.class.getResourceAsStream("/" + packName + "/" + die + ".png")).map(inputStream -> {
                try {
                    return ImageIO.read(inputStream);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            int totalWidth = images.get().mapToInt(BufferedImage::getWidth).sum();
            int maxHeight = images.get().max(Comparator.comparing(BufferedImage::getHeight)).orElseThrow().getHeight();

            BufferedImage mergedImage = new BufferedImage(totalWidth, maxHeight, BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = mergedImage.createGraphics();
            mergedImage = g2d.getDeviceConfiguration().createCompatibleImage(totalWidth, maxHeight, Transparency.TRANSLUCENT);
            g2d.dispose();
            g2d = mergedImage.createGraphics();

            var imageList = images.get().toList();
            for (int i = 0, width = 0; i < imageList.size(); i++) {
                var image = imageList.get(i);
                g2d.drawImage(image, width, 0, null);
                width += image.getWidth();
            }
            g2d.dispose();
            //todo имя должно состоять из номанала костей 13223.png
            String fileName = dice.stream().map(String::valueOf).collect(Collectors.joining());
            File outputFile = new File(fileName + ".png");

            // Save the merged image with a transparent background
            ImageIO.write(mergedImage, "PNG", outputFile);
            return outputFile;
            //todo Или только байты
           /* ByteArrayOutputStream finalBaos = new ByteArrayOutputStream();
            ImageIO.write(mergedImage, "PNG", finalBaos);
            byte[] imageBytes = finalBaos.toByteArray();*/


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
