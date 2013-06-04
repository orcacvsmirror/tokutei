package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.event.ActionEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * �_�C�A���O�����N���X
 * @author nishiyama
 */
public class DialogFactory {
	// add s.inoue 2009/12/18
	private static final String JLOGINFLAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JLoginFrameCtrl";
	// add s.inoue 2009/10/04
	private static final String JKEKKALISTFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JKekkaListFrameCtrl";  //  @jve:decl-index=0:
	private static final String JKOJINFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JKojinRegisterFrameCtrl";  //  @jve:decl-index=0:
	private static final String JOUTPUTGETUJIFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JOutputGetujiFrameCtrl";  //  @jve:decl-index=0:
	private static final String JOUTPUTNITIJIFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JOutputNitijiFrameCtrl";
	// add s.inoue 2009/12/24
	private static final String JSEARCHRESULTLISTFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JSearchResultListFrameCtrl";
	private static final String JSHOWRESULTFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JShowResultFrameCtrl";

	// eidt s.inoue 2011/05/25
	// private static final String JKENSHINPATTERNLISTFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JImportDataFrameCtrl";
	private static final String JKENSHINPATTERNLISTFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.imports.JImportDataFrameCtrl";

	// add s.inoue 2010/02/26
	private static final String JHOKENJYAMASTERMAINTENANCE = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JHokenjyaMasterMaintenanceListFrameCtrl";
	// add s.inoue 2010/02/26
	private static final String JSHIHARAIMASTERMAINTENANCE = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JShiharaiMasterMaintenanceListFrameCtrl";
	private static final String JKENSHINPATTERNMASTERMAINTENANCE = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JKenshinPatternMaintenanceListFrameCtrl";
	private static final String JTEIKEIMAINTENANCE= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JKekkaTeikeiMaintenanceListFrameCtrl";
	private static final String JKENSHINMAINTENANCE= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JKenshinMasterMaintenanceFrameCtrl";
	private static final String JKENSHINOUTPUTGETUJI= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JOutputGetujiFrameCtrl";
//    private static final String JKENSHINREGISTER = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.register.JRegisterFrameCtrl";
	private static DialogFactory instance;
	private Map<Object, IDialog> dialogEventSrcMap;

	/**
	 * �R���X�g���N�^
	 */
	private DialogFactory() {
		dialogEventSrcMap = new HashMap<Object, IDialog>();
		registDialogToMap();
	}

	/**
	 * �_�C�A���O����
	 * @param parent �_�C�A���O�\�����e�E�C���h�E
	 * @param eventSrc �C�x���g�\�[�X
	 * @return
	 */
	public IDialog createDialog(JFrame parent, Object eventSrc, ArrayList<Hashtable<String, String>> selectedRow)
		throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

		// edit s.inoue 2009/10/06
		String parentFlame = parent.getClass().getName();

		if (parentFlame.equals(JOUTPUTGETUJIFRAME) ||
				parentFlame.equals(JOUTPUTNITIJIFRAME)){
			return new MessageDialog(parent);
		// edit s.inoue 2009/10/19
		}else if (parentFlame.equals(JKEKKALISTFRAME) ||
				parentFlame.equals(JKOJINFRAME)){
			return new DateSelectDialog(parent,selectedRow);
// del s.inoue 2013/03/09
//		}else if (parentFlame.equals(JLOGINFLAME)){
//			ActionEvent e = (ActionEvent)eventSrc;
//			if (e.getActionCommand().equals("���ݒ�(F8)")){
//				return new SettingDialog(parent);
//			}else if (e.getActionCommand().equals("�X�V���(F7)")){
//				return new DngPreviewHtml(parent);
//			}else{
//				return null;
//			}
		}else{
			return null;
		}
	}
	/**
	 * �_�C�A���O����
	 * @param parent �_�C�A���O�\�����e�E�C���h�E
	 * @param eventSrc �C�x���g�\�[�X
	 * @return
	 */
	public IDialog createDialog(JFrame parent, String defaultPath)
	throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		// edit s.inoue 2009/10/06
		String parentFlame = parent.getClass().getName();

		// edit s.inoue 2010/02/26
		if (parentFlame.equals(JHOKENJYAMASTERMAINTENANCE) ||
			parentFlame.equals(JSHIHARAIMASTERMAINTENANCE) ||
			parentFlame.equals(JKENSHINPATTERNMASTERMAINTENANCE) ||
			parentFlame.equals(JTEIKEIMAINTENANCE) ||
			parentFlame.equals(JKENSHINMAINTENANCE) ||
			parentFlame.equals(JKENSHINOUTPUTGETUJI)
			){
			return new FileSelectDialog(parent,defaultPath);
		// add s.inoue 2010/10/20 �Ăяo�����Ή�������\
		}else if (parentFlame.equals(JKENSHINPATTERNLISTFRAME)){
			return new SelectKenshinPatternDialog(parent,defaultPath);
		}else{
			return null;
		}
	}

	// add s.inoue 2012/07/04
    public IDialog createDialog(JFrame jframe, ArrayList arraylist)
    throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException
	{
	    return new SelectKenshinHouhouDialog(jframe, arraylist);
	}
	/*
	 * default�\��
	 */
	public IDialog createDialog(JFrame parent){
		String parentFlame = parent.getClass().getName();

		if (parentFlame.equals(JSEARCHRESULTLISTFRAME) ||
				parentFlame.equals(JSHOWRESULTFRAME)){
			return new SelectPageDialog(parent);
		}else{
			return null;
		}
	}

	/**
	 * �C���X�^���X����
	 * @return DialogFactory�C���X�^���X
	 */
	public static DialogFactory getInstance() {
		if (instance == null) {
			instance = new DialogFactory();
		}
		return instance;
	}

	/**
	 * �_�C�A���O�N���X���}�b�v�ɓo�^����
	 * �����I�ɂ̓_�C�A���O�e�E�C���h�E�A�_�C�A���O�\���g���K�[�C�x���g�A
	 * �C�x���g�\�[�X�̒�`�t�@�C����ǂݍ��݁A�o�^����B
	 */
	private void registDialogToMap() {
	}
}
