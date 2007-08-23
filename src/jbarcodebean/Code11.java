package jbarcodebean;


public class Code11 extends AbstractBarcodeStrategy {
    
    static final CharacterCode[] codes={
        new CharacterCode('0',new byte[]{1,1,1,1,2,1},0),    
        new CharacterCode('1',new byte[]{2,1,1,1,2,1},1),    
        new CharacterCode('2',new byte[]{1,2,1,1,2,1},2),    
        new CharacterCode('3',new byte[]{2,2,1,1,1,1},3),    
        new CharacterCode('4',new byte[]{1,1,2,1,2,1},4),    
        new CharacterCode('5',new byte[]{2,1,2,1,1,1},5),    
        new CharacterCode('6',new byte[]{1,2,2,1,1,1},6),    
        new CharacterCode('7',new byte[]{1,1,1,2,2,1},7),    
        new CharacterCode('8',new byte[]{2,1,1,2,1,1},8),    
        new CharacterCode('9',new byte[]{2,1,1,1,1,1},9),    
        new CharacterCode('-',new byte[]{1,1,2,1,1,1},10),    
        new CharacterCode('*',new byte[]{1,1,2,2,1,1},11),    
        new CharacterCode('$',new byte[]{0,1},12)};    

    
    protected String augmentWithChecksum(String text) throws BarcodeException {
        int checksumC=0;
        int checksumK=0;
        int weightC=1;
        int weightK=1;
        for(int i=text.length()-1;i>=0;i--){
            CharacterCode cc = getCharacterCode(text.charAt(i));
            checksumC+=cc.check*weightC;
            checksumK+=cc.check*weightK;
            weightC++;
            weightK++;
            if(weightC>10){
                weightC=1;
            }
            if(weightK>9){
                weightK=1;
            }
        }
        checksumC=checksumC%11;
        checksumK=checksumK%11;
        CharacterCode codeC=getCharacterCode(checksumC);
        CharacterCode codeK=getCharacterCode(checksumK);
        if(text.length()>=10){
            text=text+codeC.character+codeK.character;
        } else{
            text=text+codeC.character;
        }
        StringBuffer buf=new StringBuffer(text.length()+text.length()-1);
        for(int i=0;i<text.length();i++){
            buf.append(text.charAt(i));
//            buf.append('$');
        }
        return buf.toString();
    }

    protected String getBarcodeLabelText(String text) {
        return text;
    }

    protected CharacterCode[] getCodes() {
        return codes;
    }

    protected byte getMarginWidth() {
        return 0;
    }

    protected char getStartSentinel() {
        return '*';
    }

    protected char getStopSentinel() {
        return '*';
    }

    protected boolean isInterleaved() {
        return false;
    }

    protected String postprocess(String text) {
        return text;
    }

    protected String preprocess(String text) throws BarcodeException {
        return text;
    }

    public int requiresChecksum() {
        return OPTIONAL_CHECKSUM;
    }

}
