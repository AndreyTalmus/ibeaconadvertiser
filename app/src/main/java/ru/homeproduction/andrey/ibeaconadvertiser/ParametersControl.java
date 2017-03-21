package ru.homeproduction.andrey.ibeaconadvertiser;

import java.util.UUID;

public class ParametersControl {

    public static String status = "";

    public static boolean checkAllParameters(String uuid,String major,String minor){
        boolean resultUUID = checkUUID(uuid);
        boolean resultMajor = checkMajor(major);
        boolean resultMinor = checkMinor(minor);

        if(resultUUID && resultMajor && resultMinor){
            status = status + "Данные введены корректно" + "\r\n";
            return true;
        }
        else return false;
    }
    public static boolean checkUUID(String uuid){

        char[] massiveCharUUID = uuid.toCharArray();
        if( massiveCharUUID.length == 32){
            if(isHexadecimal(uuid)){
                String mUUID =  uuid.replaceFirst( "([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5" );
                UUID myUuid = UUID.fromString(mUUID);
                return true;
            }
            else{
                status = status + "UUID может содержать только символы 0-9,a,b,c,d,e,f. Прописные или строчные не имеет значения."+"\r\n";
                return false;
            }

        }
        else{
            status = status + "Некорректный размер UUID. UUID должен быть 32 символа."+"\r\n";
            return false;
        }

    }

    public static boolean checkMajor(String Major){
        if(!Major.equals("")){
            if(Integer.parseInt(Major) >= 0 && Integer.parseInt(Major) <= 65535){
                return true;
            }
            else{
                status = status + "Major должен быть в пределах 0-65535."+"\r\n";
                return false;
            }
        }
        else{
            status = status + "Major не введен." +"\r\n";
            return false;
        }
    }

    public static boolean checkMinor(String Minor){
        if(!Minor.equals("")){
            if(Integer.parseInt(Minor) >= 0 && Integer.parseInt(Minor) <= 65535){
                return true;
            }
            else{
                status = status + "Minor должен быть в пределах 0-65535."+"\r\n";
                return false;
            }
        }
        else{
            status = status + "Minor не введен." +"\r\n";
            return false;
        }
    }

    public static boolean isHexadecimal(String text) {
        text = text.trim();

        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F' };

        int hexDigitsCount = 0;

        for (char symbol : text.toCharArray()) {
            for (char hexDigit : hexDigits) {
                if (symbol == hexDigit) {
                    hexDigitsCount++;
                    break;
                }
            }
        }

        return true ? hexDigitsCount == text.length() : false;
    }

}





