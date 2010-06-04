package jp.or.med.orca.jma_tokutei.common.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class JSimpleTree extends JTree{

	public JSimpleTree(Vector vc){
		super(vc);
	}
	/**
     * �w�肳�ꂽ�m�[�h�z���̃m�[�h�����X�g�ɂ��ĕԂ��B
     */
    public ArrayList getAllNode(DefaultMutableTreeNode node) {
        assert node != null;
        ArrayList result = new ArrayList();
        result.add(node);
        for (int i=0; i<node.getChildCount(); i++) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) node.getChildAt(i);
            result.addAll(getAllNode(n));
        }
        return result;
    }

    /** �I�����ꂽ�m�[�h�ɓo�^����Ă���I�u�W�F�N�g��Ԃ��B*/
    public Object getSelectionNodeObject() {
        TreePath path = getSelectionPath();
        if (path == null) return null;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        return node.getUserObject();
    }

    /**
     * �w�肳�ꂽ�m�[�h������ڂ���Ԃ��B
     * ������Ȃ������ꍇ�A(-1)��Ԃ��B
     */
    public int getRow(DefaultMutableTreeNode node) {
        for (int r=0; r<getRowCount(); r++) {
            Object obj = getPathForRow(r).getLastPathComponent();
            if (obj == node) return r;
        }
        return -1;  // ������Ȃ�����
    }
}
