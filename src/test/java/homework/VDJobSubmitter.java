package homework;

public class VDJobSubmitter {
    public static String oriString2ETLString(String ori) {
        StringBuffer etlString = new StringBuffer();
        String[] splits=ori.split("\t");
        if (splits.length<9) {
            return null;
        }
        splits[3] = splits[3].replace(" ", "");
        for (int i = 0; i < splits.length; i++) {
            if (i < 9) {
                if (i == splits.length - 1) {
                    etlString.append(splits[i]);
                }else {
                    etlString.append(splits[i] + "\t");
                }
            }else {
                if (i == splits.length - 1) {
                    etlString.append(splits[i]);
                }else {
                    etlString.append(splits[i] + "&");
                }
            }
        }
        return etlString.toString();
    }
}
