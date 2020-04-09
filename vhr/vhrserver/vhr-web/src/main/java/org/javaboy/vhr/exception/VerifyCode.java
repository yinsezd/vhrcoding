package org.javaboy.vhr.exception;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码的生成工具类
 * 登录验证码
 */
public class VerifyCode {
    private int width = 100;//生成验证码的宽度
    private int height = 50;//生成验证码的高度
    private String[] fontName = {"宋体","楷体","隶书","微软雅黑"};
    private Color bgColor = new Color(255,255,255); //定义验证框的背景色
    private Random random = new Random();
    private String codes = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String text; // 记录随机字符串

    /**
     * 随机获取一个颜色
     */
    public Color randomColor(){
        int red = random.nextInt(150);
        int green = random.nextInt(150);
        int blue = random.nextInt(150);
        return new Color(red,green,blue);
    }

    /**
     * 随机获取一个字符
     *
     */
    public char randomChar(){
        return codes.charAt(random.nextInt(codes.length()));
    }

    /**
     * 随机获取一种字体
     */
    public Font randomFont(){
        String name = fontName[random.nextInt(fontName.length)];
        int style = random.nextInt(4);
        int size = random.nextInt(5)+24;
        return new Font(name,style,size);
    }

    /**
     * 绘制干扰线
     */
    public void dramLine(BufferedImage bufferedImage){
        Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
        int num = 5;
        for(int i= 0;i<num;i++){
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g2.setColor(randomColor());
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(x1,y1,x2,y2);
        }
    }

    /**
     * 创建一个空白的BufferedImage对象
     */
    public BufferedImage createImage(){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D g2 = (Graphics2D)image.getGraphics();
        g2.setColor(bgColor);
        g2.fillRect(0,0,width,height);
        return image;
    }

    public BufferedImage getImage(){
        BufferedImage image = createImage();
        Graphics2D g2 = (Graphics2D)image.getGraphics();
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<4;i++){
            String s = randomChar()+"";
            sb.append(s);
            g2.setColor(randomColor());
            g2.setFont(randomFont());
            float x = i * width * 1.0f/4;
            g2.drawString(s,x,height-15);
        }
        this.text = sb.toString();
        dramLine(image);
        return image;
    }

    public String getText(){
        return text;
    }

    public static void output(BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image,"JPEG",out);
    }

}
