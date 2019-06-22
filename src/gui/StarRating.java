package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class StarRating extends JPanel implements MouseListener, MouseMotionListener  {
	private List<JLabel> starLabels = Arrays.asList(new JLabel(), new JLabel(), new JLabel(),
			new JLabel(), new JLabel());
	private ImageIcon defaultIcon;
	private ImageIcon selectedIcon;
	private final static int gap = 1;
	private int clicked = -1;

	public StarRating() {
		super(new GridLayout(1, 5, 2*gap, 2*gap));
		this.defaultIcon = new ImageIcon("31g.png");
		ImageProducer ip = defaultIcon.getImage().getSource();
		this.selectedIcon = makeStarImageIcon(ip, 1f, 1f, 0f);
		
		
		starLabels.forEach(x -> x.setIcon(defaultIcon));
		starLabels.forEach(x -> add(x));
		starLabels.forEach(x -> x.setBackground(new Color(200, 200, 200)));
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	private static ImageIcon makeStarImageIcon(ImageProducer ip, float rf, float gf, float bf) {
        return new ImageIcon(Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(ip, new SelectedImageFilter(rf, gf, bf))));
    }

	public void repaintIcon(int index) {
		for(int i=0; i<starLabels.size(); i++) {
			starLabels.get(i).setIcon((i <= index) ? selectedIcon : defaultIcon);
		}
		repaint();
	}

	private int getSelectedIconIndex(Point p) {
		for(int i=0; i<starLabels.size(); i++) {
			Rectangle r = starLabels.get(i).getBounds();
			r.grow(gap, gap);
			if(r.contains(p)) return i;
		}
		return -1;
	}

	public void clear() {
		clicked = -1;
		repaintIcon(clicked);
	}

	public int getLevel() {
		return clicked;
	}

	public void setLevel(int l) {
		clicked = l;
		repaintIcon(clicked);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clicked = getSelectedIconIndex(e.getPoint());	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		repaintIcon(getSelectedIconIndex(e.getPoint()));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		repaintIcon(clicked);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		repaintIcon(getSelectedIconIndex(e.getPoint()));
	}

static class SelectedImageFilter extends RGBImageFilter {
		private final float rf;
		private final float gf;
		private final float bf;
		protected SelectedImageFilter(float rf, float gf, float bf) {
			super();
			this.rf = Math.min(1f, rf);
			this.gf = Math.min(1f, gf);
			this.bf = Math.min(1f, bf);
			canFilterIndexColorModel = false;
		}

		@Override public int filterRGB(int x, int y, int argb) {
			int r = (int) (((argb >> 16) & 0xFF) * rf);
			int g = (int) (((argb >>  8) & 0xFF) * gf);
			int b = (int) (((argb)       & 0xFF) * bf);
			return (argb & 0xFF000000) | (r << 16) | (g << 8) | (b);
		}
	}
}

