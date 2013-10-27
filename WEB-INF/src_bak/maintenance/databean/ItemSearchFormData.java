package maintenance.databean;

import common.jsp.databean.GenericSearchFormData;


public class ItemSearchFormData extends GenericSearchFormData
{
	public static int maxFields = 7;
	public static final int itemNo = 1;
	public static final int itemDescription = 2;
	public static final int amount = 3;
	public static final int maxQty = 4;
	public static final int itemType = 5;
	public static final int paymentType = 6;
	public static final int shortCut = 7;
	
	public ItemSearchFormData()
	{
		super(maxFields);
		this.setData(this.shortCut, "IM");
	}
	
	protected void initLabels()
	{
		setLabel(this.itemNo, "itemNo","Item No.","");
		setLabel(this.itemDescription, "itemDescription","Item Description","");
		setLabel(this.amount, "amount","Amount","NUMBER");
		setLabel(this.maxQty, "maxQty","Max Quantity","NUMBER");
		setLabel(this.itemType, "itemType","Item Type","");
		setLabel(this.paymentType, "paymentType","Payment Type","");
		setLabel(this.shortCut,"shortcut","","");
	}
}