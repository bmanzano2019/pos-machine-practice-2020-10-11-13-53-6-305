package pos.machine;

import java.util.*;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<TalliedItemInfo> talliedBarcodeData = tallyBarcodeData(barcodes);

        return generateReceiptString(talliedBarcodeData);
    }

    private String generateReceiptString(List<TalliedItemInfo> talliedBarcodeData) {
        String formattedData = "***<store earning no money>Receipt***\n";
        int receiptTotal = 0;

        for (TalliedItemInfo item : talliedBarcodeData) {
            int subtotal = item.getPrice() * item.getItemCount();
            receiptTotal += subtotal;
            formattedData += "Name: " + item.getName() + ", Quantity: " + item.getItemCount() + ", Unit price: " + item.getPrice() + " (yuan), Subtotal: " + subtotal + " (yuan)\n";
        }
        formattedData += "----------------------\n" + "Total: " + receiptTotal + " (yuan)\n**********************";

        return formattedData;
    }

    private List<TalliedItemInfo> tallyBarcodeData(List<String> barcodes) {
        List<TalliedItemInfo> talliedBarcodeData = new ArrayList<>();
        Map<String, Integer> tallyBarcodes = tallyCommonBarcodes(barcodes);

        for (Map.Entry<String, Integer> talliedBarcode : tallyBarcodes.entrySet()) {
            ItemInfo itemInfo = getBarcodeData(talliedBarcode.getKey());
            talliedBarcodeData.add(new TalliedItemInfo(talliedBarcode.getKey(), itemInfo.getName(), itemInfo.getPrice(), talliedBarcode.getValue()));
        }

        return talliedBarcodeData;
    }

    private ItemInfo getBarcodeData(String barcode) {
        List<ItemInfo> itemInfoList = ItemDataLoader.loadAllItemInfos();

        for (ItemInfo itemInfo : itemInfoList) {
            if (itemInfo.getBarcode() == barcode) {
                return itemInfo;
            }
        }
        // assume that all barcodes are valid and exist in the database
        return null;
    }

    private Map<String,Integer> tallyCommonBarcodes(List<String> barcodes) {
        HashMap<String, Integer> tallyBarcodes = new HashMap<String, Integer>();

        for (String barcode : barcodes) {
            Integer count = tallyBarcodes.get(barcode);
            tallyBarcodes.put(barcode, (count == null) ? 1 : count + 1);
        }

        // using treemap to sort the generated hashmap
        return new TreeMap<String, Integer>(tallyBarcodes);
    }

}
