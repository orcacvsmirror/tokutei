package jp.or.med.orca.jma_tokutei.common.component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * ダイアログ生成クラス
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

	// add s.inoue 2010/02/26
	private static final String JHOKENJYAMASTERMAINTENANCE = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JHokenjyaMasterMaintenanceListFrameCtrl";
	// add s.inoue 2010/02/26
	private static final String JSHIHARAIMASTERMAINTENANCE = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JShiharaiMasterMaintenanceListFrameCtrl";
	private static final String JKENSHINPATTERNMASTERMAINTENANCE = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JKenshinPatternMaintenanceListFrameCtrl";
	private static final String JTEIKEIMAINTENANCE= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JKekkaTeikeiMaintenanceListFrameCtrl";
	private static final String JKENSHINMAINTENANCE= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JKenshinMasterMaintenanceFrameCtrl";
	private static final String JKENSHINOUTPUTGETUJI= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JOutputGetujiFrameCtrl";

	private static DialogFactory instance;
	private Map<Object, IDialog> dialogEventSrcMap;

	/**
	 * コンストラクタ
	 */
	private DialogFactory() {
		dialogEventSrcMap = new HashMap<Object, IDialog>();
		registDialogToMap();
	}

	/**
	 * ダイアログ生成
	 * @param parent ダイアログ表示元親ウインドウ
	 * @param eventSrc イベントソース
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
		}else if (parentFlame.equals(JLOGINFLAME)){
			return new SettingDialog(parent);
		}else{
			return null;
		}
	}
	/**
	 * ダイアログ生成
	 * @param parent ダイアログ表示元親ウインドウ
	 * @param eventSrc イベントソース
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
		}else{
			return null;
		}
	}


	public IDialog createDialog(JFrame parent){
		// edit s.inoue 2009/10/06
		String parentFlame = parent.getClass().getName();

		// edit s.inoue 2010/04/21
		if (parentFlame.equals(JSEARCHRESULTLISTFRAME) ||
				parentFlame.equals(JSHOWRESULTFRAME)){
			return new PageSelectDialog(parent);
		}else{
			return null;
		}
	}

	/**
	 * インスタンス生成
	 * @return DialogFactoryインスタンス
	 */
	public static DialogFactory getInstance() {
		if (instance == null) {
			instance = new DialogFactory();
		}
		return instance;
	}

	/**
	 * ダイアログクラスをマップに登録する
	 * 将来的にはダイアログ親ウインドウ、ダイアログ表示トリガーイベント、
	 * イベントソースの定義ファイルを読み込み、登録する。
	 */
	private void registDialogToMap() {
	}
}
