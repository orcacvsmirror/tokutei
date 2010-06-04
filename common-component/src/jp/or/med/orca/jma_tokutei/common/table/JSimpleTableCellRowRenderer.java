package jp.or.med.orca.jma_tokutei.common.table;

import java.awt.*;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

public class JSimpleTableCellRowRenderer implements TableCellRenderer {
	private static final DotBorder dotBorder = new DotBorder(2,2,2,2);
    private static final Border emptyBorder  = BorderFactory.createEmptyBorder(2,2,2,2);
    private final DefaultTableCellRenderer delegate;

    private static final String[] COLUMN_ARIGNMENT_CENTER = {
    "�N�x","���N����","����","����","���f���{��","�����","���ʒʒm��",
    "�X�V���t","�K�{�t���O", "H/L","�X�V����","�o�b�N�A�b�v���t","�L��/����","�L�������J�n","�L�������I��","����"};
    private static final String[] COLUMN_ARIGNMENT_RIGHT = {
    "��l�����i�j���j","��l����i�j���j","��l�����i�����j","��l����i�����j","���͉����l", "���͏���l","�o�b�N�A�b�v�T�C�Y","����ԍ�"};
    private static final String[] COLUMN_ARIGNMENT_LEFT = {
    "��f�������ԍ�","�����i�J�i�j","�����i�����j","��ی��ҏؓ��L��","��ی��ҏؓ��ԍ�","�ی��Ҕԍ�","��s�@�֔ԍ�",
    "���茋��","�ی��w�����x��","�����R�����g","���ڔԍ�","���ږ�","���ڃR�[�h","�������@","��l�͈�","�P��",
    "���l","�ی��Җ���","�X�֔ԍ�","���ݒn","�d�b�ԍ�","�Z��","���ڔԍ�","���ږ�","���f�p�^�[��No","���f�p�^�[������","��f�ҕR�t��ID",
    "�x����s�@�֔ԍ�","�x����s�@�֖���","���ʁi�R�[�h�j", "���ʁi������j" ,"���{�敪", "����", "�R�����g","���[�U��","����","�������","����No","����","�ϑ��P���敪","�������No"};
    private static final String[] COLUMN_ARIGNMENT_RIGHT_CUNMA = {
    "�P�����v","�������S���v","�������z���v","�P���i�~�j","���ʁi���l�j",
    "�P���i��{�I�Ȍ��f�j","�P���i�n�������j","�P���i�S�d�}�����j","�P���i��ꌟ���j","�P���i�l�ԃh�b�N�j"};
    private static final String[] COLUMN_ARIGNMENT_CENTEER_INPUT = {"�K�{�t���O[����]"};
    private static final String[] COLUMN_ARIGNMENT_LEFT_INPUT = {"���l[����]"};
    private static final String[] COLUMN_ARIGNMENT_RIGHT_INPUT = {
    	"��l�����i�j���j[����]","��l����i�j���j[����]","��l�����i�����j[����]","��l����i�����j[����]","�P���i�~�j[����]","���l[����]"};

    private final Color COLOR_ABLE = ViewSettings.getAbleItemBgColor();
    private final Color COLOR_DEFAULT = Color.WHITE;

    private Vector<JSimpleTableCellRendererData> cellColors = new Vector<JSimpleTableCellRendererData>();

    public JSimpleTableCellRowRenderer(DefaultTableCellRenderer delegate) {
        this.delegate = delegate;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	// edit s.inoue 2009/11/18 �t�B�[���h�l�������ꍇ�ɂ���l�ɒu��������
    	if (value == null)
    		value ="";

        Component c = delegate.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
        if(c instanceof JComponent) {
            int lsi = table.getSelectionModel().getLeadSelectionIndex();
            ((JComponent)c).setBorder(row==lsi?dotBorder:emptyBorder);
            dotBorder.setLastCellFlag(row==lsi&&column==table.getColumnCount()-1);

            String headString = (String)table.getColumnModel().getColumn(column).getHeaderValue();
            // edit s.inoue 2009/11/16
            setJSimpleTableCellSettings(c,headString,isSelected);
        }
        return c;
    }

    // �t�B�[���h���E�񂹐ݒ�
    private Component setJSimpleTableCellSettings(Component c,String headString,boolean isSelected){

    	for (String modelhead : COLUMN_ARIGNMENT_CENTER){
        	if(headString.equals(modelhead)){
        		delegate.setHorizontalAlignment(SwingConstants.CENTER);
        		if (!isSelected)
        			delegate.setBackground(COLOR_DEFAULT);
        		return c;
        	}
        }

        for (String modelhead : COLUMN_ARIGNMENT_RIGHT){
        	if(headString.equals(modelhead)){
        		delegate.setHorizontalAlignment(SwingConstants.RIGHT);
        		if (!isSelected)
        			delegate.setBackground(COLOR_DEFAULT);
        		return c;
        	}
        }
        for (String modelhead : COLUMN_ARIGNMENT_LEFT){
        	if(headString.equals(modelhead)){
        		delegate.setHorizontalAlignment(SwingConstants.LEFT);
        		if (!isSelected)
        			delegate.setBackground(COLOR_DEFAULT);
        		return c;
        	}
        }
        for (String modelhead : COLUMN_ARIGNMENT_RIGHT_CUNMA){
        	if(headString.equals(modelhead)){
    			delegate.setHorizontalAlignment(SwingConstants.RIGHT);
    			return c;
        	}
        }
        // ���̓t�B�[���h
        for (String modelhead : COLUMN_ARIGNMENT_CENTEER_INPUT){
        	if(headString.equals(modelhead)){
        		delegate.setHorizontalAlignment(SwingConstants.CENTER);
        		if (!isSelected)
    			delegate.setBackground(COLOR_ABLE);
    			return c;
        	}
        }
        for (String modelhead : COLUMN_ARIGNMENT_LEFT_INPUT){
        	if(headString.equals(modelhead)){
        		delegate.setHorizontalAlignment(SwingConstants.LEFT);
        		if (!isSelected)
    			delegate.setBackground(COLOR_ABLE);
    			return c;
        	}
        }
        for (String modelhead : COLUMN_ARIGNMENT_RIGHT_INPUT){
        	if(headString.equals(modelhead)){
        		delegate.setHorizontalAlignment(SwingConstants.RIGHT);
        		if (!isSelected)
    			delegate.setBackground(COLOR_ABLE);
    			return c;
        	}
        }
        return c;
    }
}

class DotBorder extends EmptyBorder {
    private static final BasicStroke dashed = new BasicStroke(
        1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
        10.0f, (new float[] {1.0f}), 0.0f);
    private static final Color dotColor = new Color(200,150,150);
    public DotBorder(int top, int left, int bottom, int right) {
        super(top, left, bottom, right);
    }
    private boolean isLastCell = false;
    public void setLastCellFlag(boolean flag) {
        isLastCell = flag;
    }
    @Override
    public boolean isBorderOpaque() {
        return true;
    }
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        Graphics2D g2 = (Graphics2D)g;
        g2.translate(x,y);
        g2.setPaint(dotColor);
        g2.setStroke(dashed);
        int cbx = c.getBounds().x;
        if(cbx==0) {
            g2.drawLine(0,0,0,h);
        }
        if(isLastCell) {
            g2.drawLine(w-1,0,w-1,h);
        }
        if(cbx%2==0) {
            g2.drawLine(0,0,w,0);
            g2.drawLine(0,h-1,w,h-1);
        }else{
            g2.drawLine(1,0,w,0);
            g2.drawLine(1,h-1,w,h-1);
        }
        g2.translate(-x,-y);
    }
}
