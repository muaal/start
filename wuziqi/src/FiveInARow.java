import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class FiveInARow extends JFrame implements MouseListener {
    private Vector v = new Vector();    //���е�ÿ��������Ϣ
    private Vector white = new Vector(); //�׷�������Ϣ
    private Vector black = new Vector(); //�ڷ�������Ϣ
    boolean b; //�����жϰ��컹�Ǻ���
    private int whiteCount, blackCount; //������岽��
    private int w = 25;    //����С
    private int px = 100, py = 100;    //���̵Ĵ�С
    private int pxw = px + w, pyw = py + w;
    private int width = w * 16, height = w * 16;
    private int vline = width + px;    //��ֱ�ߵĳ���
    private int hline = height + py; //ˮƽ�ߵĳ���

    /**
     * ���췽��
     */
    FiveInARow() {
        super("������������");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�رհ�ť
        Container con = this.getContentPane();
        con.setLayout(new BorderLayout());
        this.addMouseListener(this);//��Ӽ���
        this.setSize(600, 600);//���ô����С
        this.setBackground(Color.orange);
        this.setVisible(true);
    }

    /**
     * �����̺�����
     *
     * @param g
     */
    public void paint(Graphics g) {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());//�������
        g.setColor(Color.BLACK);//����������ɫ
        g.drawRect(px, py, width, height);//�����С
        g.drawString("������������С��Ϸ���һ����Ի��壬��ӭʹ��", 180, 70);

        for (int i = 0; i < 15; i++) {
            g.drawLine(pxw + i * w, py, pxw + i * w, hline);//ÿ�����ߺ�����
            g.drawLine(px, pyw + i * w, vline, pyw + i * w);
        }

        for (int x = 0; x < v.size(); x++) {
            String str = (String) v.get(x);
            String tmp[] = str.split("-");
            int a = Integer.parseInt(tmp[0]);
            int b = Integer.parseInt(tmp[1]);
            a = a * w + px;
            b = b * w + py;
            if (x % 2 == 0) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.BLACK);
            }
            g.fillArc(a - w / 2, b - w / 2, w, w, 0, 360);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            int x = e.getX();
            int y = e.getY();
            x = (x - x % w) + (x % w > w / 2 ? w : 0);
            y = (y - y % w) + (y % w > w / 2 ? w : 0);
            x = (x - px) / w;
            y = (y - py) / w;

            if (x >= 0 && y >= 0 && x <= 16 && y <= 16) {
                if (v.contains(x + "-" + y)) {
                    System.out.println("�Ѿ������ˣ�");
                } else {
                    v.add(x + "-" + y);
                    this.repaint();
                    if (v.size() % 2 == 0) {
                        black.add(x + "-" + y);
                        this.victory(x, y, black);
//						System.out.println("����");
                    } else {
                        white.add(x + "-" + y);
                        this.victory(x, y, white);
//						System.out.println("����");
                    }
//					System.out.println(e.getX()+"-"+e.getY());
                }
            }  //				System.out.println(e.getX()+"-"+e.getY()+"|"+x+"-"+y+"\t�����߽���");

        }

        if (e.getButton() == MouseEvent.BUTTON3) {    //�һ�����ķ���
//			System.out.println("����һ�--����");
            if (v.isEmpty()) {
                JOptionPane.showMessageDialog(this, "û����ɻ�");
            } else {
                if (v.size() % 2 == 0) {    //�ж��ǰ�����壬���Ǻ������
                    blackCount++;
                    if (blackCount > 3) {
                        JOptionPane.showMessageDialog(this, "�����Ѿ�����3��");
                    } else {
                        v.remove(v.lastElement());
                        this.repaint();
                    }
                } else {
                    whiteCount++;
                    if (whiteCount > 3) {
                        JOptionPane.showMessageDialog(this, "�����Ѿ�����3��");
                    } else {
                        v.remove(v.lastElement());
                        this.repaint();
                    }
                }
            }
        }
    }

    /**
     * �ж�ʤ���ķ���
     *
     * @param x
     * @param y
     * @param contain
     */
    private void victory(int x, int y, Vector contain) {
        int cv = 0;    //��ֱ������������
        int ch = 0;    //ˮƽ������������
        int ci1 = 0; //б�淽����������1
        int ci2 = 0; //б�淽����������2

        //����ˮƽ������������
        for (int i = 1; i < 5; i++) {
            if (contain.contains((x + i) + "-" + y)) {
                ch++;
            } else {
                break;
            }
        }
        for (int i = 1; i < 5; i++) {
            if (contain.contains((x - i) + "-" + y)) {
                ch++;
            } else {
                break;
            }
        }

        //���㴹ֱ������������
        for (int i = 1; i < 5; i++) {
            if (contain.contains(x + "-" + (y + i))) {
                cv++;
            } else {
                break;
            }
        }
        for (int i = 1; i < 5; i++) {
            if (contain.contains(x + "-" + (y - i))) {
                cv++;
            } else {
                break;
            }
        }

        //����45��б�淽����������
        for (int i = 1; i < 5; i++) {
            if (contain.contains((x + i) + "-" + (y + i))) {
                ci1++;
            } else {
                break;
            }
        }
        for (int i = 1; i < 5; i++) {
            if (contain.contains((x - i) + "-" + (y - i))) {
                ci1++;
            } else {
                break;
            }
        }

        //����135��б�淽����������
        for (int i = 1; i < 5; i++) {
            if (contain.contains((x + i) + "-" + (y - i))) {
                ci2++;
            } else {
                break;
            }
        }
        for (int i = 1; i < 5; i++) {
            if (contain.contains((x - i) + "-" + (y + i))) {
                ci2++;
            } else {
                break;
            }
        }

        if (ch >= 4 || cv >= 4 || ci1 >= 4 || ci2 >= 4) {
            System.out.println(v.size() + "����");
            if (v.size() % 2 == 0) {
                //�ж��Ǻ���Ӯ�����ǰ���Ӯ
                JOptionPane.showMessageDialog(null, "����Ӯ��");
            } else {
                JOptionPane.showMessageDialog(null, "����Ӯ��");
            }
            this.v.clear();
            this.black.clear();
            this.white.clear();
            this.repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}
