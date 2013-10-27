package maintenance.databean;

import common.jsp.databean.GenericWebFormData;
import java.sql.ResultSet;

public class ItemData extends GenericWebFormData
{
	public static int maxFields = 7;
	public static final int itemNo = 1;
	public static final int itemDescription = 2;
	public static final int amount = 3;
	public static final int maxQty = 4;
	public static final int itemType = 5;
	public static final int paymentType = 6;
	public static final int shortCut = 7;
	
	public ItemData()
	{
		super(maxFields);
		this.setShortcut("IM");
	}
	
	public ItemData(ResultSet rs)
	{
		super(maxFields);
		this.setShortcut("IM");
		try
		{
			this.setData(ItemData.itemNo, rs.getString("itemno"));
			this.setData(ItemData.itemDescription, rs.getString("itemdescription"));
			this.setData(ItemData.amount, rs.getString("amount"));
			this.setData(ItemData.maxQty, rs.getString("maxqty"));
			this.setData(ItemData.itemType, rs.getString("itemtype"));
			this.setData(ItemData.paymentType, rs.getString("paymenttype"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void initLabels()
	{
		setLabel(this.itemNo, "itemNo","Item No.","");
		setLabel(this.itemDescription, "itemDescription","Item Description","REQUIRED");
		setLabel(this.amount, "amount","Amount","NUMBER");
		setLabel(this.maxQty, "maxQty","Max Quantity","NUMBER");
		setLabel(this.itemType, "itemType","Item Type","");
		setLabel(this.paymentType, "paymentType","Payment Type","");
		setLabel(this.shortCut,"shortcut","","");
	}
}