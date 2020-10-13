package pos.machine;

public class TalliedItemInfo extends ItemInfo {
    private final int itemCount;

    public TalliedItemInfo(String barcode, String name, int price, int itemCount) {
        super(barcode, name, price);
        this.itemCount = itemCount;
    }

    public int getItemCount() {
        return itemCount;
    }

}
