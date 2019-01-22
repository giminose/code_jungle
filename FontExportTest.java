/**
 * @author by gimme on 18/7/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:app.xml")
public class FontExport {

    @Test
    public void wtf() throws Exception {
        Font eudc = Font.createFont(Font.TRUETYPE_FONT, new File("/Users/gimme/workshop/systex/rare-font/屏東/EUDC.TTE"));
        Font eudc64 = eudc.deriveFont(Font.PLAIN, 64);
        createImage("\uE000", eudc64, new File("/Users/gimme/Desktop/a.png"), 64, 64);
    }

    public static void createImage(String str, Font font, File outFile,
                                   Integer width, Integer height) throws Exception {
        // 创建图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setClip(0, 0, width, height);
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景
        g.setColor(Color.black);// 在换成黑色
        g.setFont(font);// 设置画笔字体
        g.drawString("" + str, 0, height - 10);
//        /** 用于获得垂直居中y */
//        Rectangle clip = g.getClipBounds();
//        FontMetrics fm = g.getFontMetrics(font);
//        int ascent = fm.getAscent();
//        int descent = fm.getDescent();
//        int y = (clip.height - (ascent + descent)) / 2 + ascent;
//        for (int i = 0; i < 6; i++) {// 256 340 0 680
//            g.drawString(str, i * 680, y);// 画出字符串
//        }
        g.dispose();
        ImageIO.write(image, "png", outFile);// 输出png图片
    }
}