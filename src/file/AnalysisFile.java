package file;

import Common.FileUtil;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Wayne
 * @Classname AnalysisFile
 * @Description TODO
 * @Date 20/4/11 下午2:27
 * @Version V1.0
 */
public class AnalysisFile {

    public static void main(String[] args) {
        String filePath = "/Users/wangyang/Downloads/CheckTariffe.txt";
        ArrayList<String> recordIdList = new ArrayList<>();
        FileUtil.readLines(filePath, recordIdList);
//        String line = recordIdList.get(0);
        ArrayList<String> lines = new ArrayList<>();

        for (String line : recordIdList) {
            String[] arr = line.split("\\|");
            if(arr.length != 3) {
                System.out.println("格式不对");
            }
            String rpId = arr[0];
            String accNbr = arr[1];
            String date = arr[2];
            String currMonth = date.replaceAll("-", "").substring(0, 6);
            String key = rpId + "|" + accNbr + "|" + currMonth;
            if (!lines.contains(key)) {
                lines.add(key);
            }
        }
//        while (true) {
//            if (line.indexOf("|") > 0) {
//                String line1 = line.substring(0, line.indexOf("|") + 11);
//                line = line.substring(line.indexOf("|")  + 11);
//                lines.add(line1);
//            } else {
//                break;
//            }
//
//        }

        String writeFilePath = "/Users/wangyang/Downloads/analysisResult-20200821.txt";
        FileUtil.writeLines(writeFilePath, lines);

//        if (dir.isDirectory()) {
//            File[] files = dir.listFiles();
//            for (File file : files) {
//                System.out.println(file.getName());
//                ArrayList<String> lines = new ArrayList<>();
//                FileUtil.readLines(file.getAbsolutePath(), lines);
//                System.out.println(JSON.toJSONString(lines));
//                for (String line : lines) {
//                    String[] arr = line.split("\\|\\|");
//                    if (arr.length > 2) {
//                        recordIdList.add(arr[0]);
//                    }
//                }
//            }
//        }
//        System.out.println(recordIdList.size());
//        System.out.println(JSON.toJSONString(recordIdList));
//        String str = "64296882701,64332630501,64343158601,64353882501,64413237401,64496376501,64481638401,64513752101,64523963401,64525292901,64562628101,64544950301,64543889901,64632857901,64623875301,64667679501,64687921701,64749263901,64772880201,64748535001,64789318601,64844637601,64831964501,64873574201,64906452801,64967535101,65018983201,65088735001,65157556801,65199048801,65306424201,65385340401,64853885301,64852323301,64852601601,64852720401,64894402701,65122806401,65150528601,65196254801,65225014301,65275201801,65307963101,65369108801,65400683201,64221021101,64290840201,64513900501,64539716401,64544537701,64576510301,64585351001,64592175401,64641563201,64629836101,64641810701,64641961401,64657942201,64666312101,64654389201,64718727101,64734965301,64726756001,64783893301,64836309401,64836507401,64882118901,64888280901,65020617601,65216818001,65310543601,65306718901,65332750401,64899438401,64914042901,64996069001,64983366201,65111583301,65131785901,65137752301,65171335201,65178343401,65172015001,65283860901,64123318601,64121954401,64123885101,64117408101,64202803301,64195068301,64268956401,64267822301,64343129401,64352525601,64363873001,64490995801,64543926701,64630999201,64661998501,64668819401,64724849001,64776649301,64787618301,64864759101,65022003201,65058974001,65066854401,65082582201,65283043101,65293133401,65295337601,64940648101,64997936301,65080021601,65057827801,65054840201,65112235101,65131387201,65120423501,65136547301,65266557201,65368869301,64248991501,64259070301,64249000301,64277093201,64304365501,64325067501,64368692401,64419778601,64516125401,64558252101,64597193801,64596550801,64621554901,64702651301,64738374401,64739596501,64801841601,64794263701,64789990801,64800751501,64819787601,64833169601,64841590101,64998454501,64984493901,65319196401,64847358501,64819529601,64838973201,64905497401,64889656801,65078193501,65328231801,64237732801,64248630501,64333565901,64358810901,64500662501,64541651801,64540570501,64538321001,64545682201,64623430201,64638271301,64613313501,64616684901,64727375701,64720412801,64732864801,64737515601,64736769801,64752981601,64782150301,64800563201,64769055801,64832163901,64875226801,64859620001,64903726701,65008535801,64866274901,64879272501,64902777401,64940255501,65009569801,65024244901,65118166201,65130072601,65148257801,65145909301,65341175801,65318402501,64121584701,64295182301,64320750701,64318566101,64334754801,64480172601,64500543501,64519210801,64567961401,64559833501,64537620101,64575660301,64574981601,64625270601,64643124401,64618408501,64639314001,64626216301,64672554901,64668438701,64704648501,64778435401,64753373001,64804721001,64769282601,64782598901,64835324101,64808120301,64868539701,64883399601,64889532101,64932814901,64945437401,65004633901,65012880601,65224974901,65397672701,64838678001,64822555301,64932377101,64973246501,65076508301,65092554001,65148750401,65236521601,65274240601,64126264701,64307078501,64323010901,64358487901,64416084101,64469162201,64509229701,64518733901,64559002501,64558661501,64558161001,64638162001,64644297601,64649211501,64706016601,64727741601,64735674801,64737998901,64759162001,64775020501,64763681901,64812167501,64797583901,64784064901,64803411701,64816494001,64869990301,64901423701,64915322201,64940064501,65009674701,65072939301,65080528201,65330381901,64815425901,64888430501,64955341501,64976464801,65182959101,65144160901,64098841701,64221625901,64275104601,64333901001,64352582001,64425875001,64440476401,64484358701,64487112001,64531365001,64532869001,64569539401,64585466301,64680844301,64684835101,64793941601,64823328101,65014988801,65037912001,65048336401,65079549201,65121503201,65102615901,65095814901,65171842501,65353404901,65024537101,65074459501,65093362701,65053226201,65082213401,65097661501,65107822201,65111286101,65138268001,65320536101,65374435801,65393262301,64117010701,64305674601,64509399001,64547222901,64567150101,64539174201,64558454601,64607600801,64628776901,64628552501,64676122701,64717026201,64721175801,64736770101,64738620701,64746240001,64742251801,64775295401,64804556501,64813322401,64804328801,64825499401,64828950501,64833576701,64835772301,64858598801,64874571901,64853285801,64997386901,65378051801,64888323701,64959695401,65149060401,65228520701,65312990801,64242545801,64241310501,64226438501,64247517801,64303833401,64298209201,64324696001,64434725701,64514756201,64535902601,64563984501,64615824201,64641658801,64625208301,64661223401,64666703601,64680767201,64797852201,64797767501,64860463101,64866248001,64915393801,64996984101,65094497001,65093493701,65162792601,65314443001,65369554101,64874014001,65091376301,65080086901,65122924301,65166125601,65161296601,65238997301,65258903001,64176522001,64203407101,64211528401,64237748001,64254867701,64284368601,64285137501,64362308701,64415618001,64490276501,64467943201,64570741101,64570826901,64552113001,64543878401,64551237401,64607818101,64577479001,64650847501,64611922201,64665427301,64677719401,64695655301,64733889101,64730902601,64732979001,64774802101,64787707301,64894528701,65026413201,65061303701,65098950501,65107736201,65233835801,65283522801,65289304401,65340554501,65311458401,65302416401,65370125401,65394688801,64941601001,65007339201,65067951001,65112235201,65137338301,65194451401,65333759401,65321316201,65378510001,64126289301,64235600701,64235910901,64230759101,64249667501,64271709901,64257514901,64279433501,64478688601,64553563401,64558589301,64623404601,64658392801,64671466901,64685696501,64711316601,64786899801,64824282701,64860519001,64964686201,65004534801,65121651801,65321552601,64946082401,65080308901,65114024401,65114623901,65216545501,64241773401,64241621601,64285843201,64289576101,64297773301,64284031001,64333958301,64335706801,64349206001,64366300001,64441179201,64426714201,64522115001,64563887501,64637659001,64681973601,64776947601,64793669801,64791126001,64819140701,64860824801,64883417101,64992750001,65011805801,65032612301,65065943401,65223709801,65264364201,65301888501,64852764001,64880733101,65056985601,65103408901,65109619501,65137961001,65149117201,65283870501,65392039501,64124083901,64125771601,64266453901,64327194501,64328282401,64499257901,64527188801,64548151501,64596265801,64595451801,64668864701,64647602801,64701084501,64702589601,64733233401,64739674701,64728346101,64766201501,64801099001,64812601701,64801238701,64904494301,64892150401,64953345601,64875356701,64870186701,64893285601,64952014601,65325830901,64118078601,64307665801,64315098501,64446794901,64498250701,64554030601,64593270901,64608403601,64616202601,64666064501,64666471501,64669993701,64677011701,64647298501,64645585801,64721860901,64727112301,64752297901,64765045801,64792590901,64796086701,64803395101,64815631501,64857488701,64866565901,64865465901,64958268501,64976463601,65118892701,65169395901,65207695701,65218487801,65294908201,64829732401,64818759901,64902259801,64999720901,65017083301,65049240701,65148191201,64213028701,64238524501,64343820901,64353354601,64426568401,64479601601,64570339001,64603656301,64598067201,64623721401,64641205901,64637779401,64644813901,64665602801,64658357101,64725293701,64739548101,64751174001,64784304901,64811401201,64818483501,64848297901,64895833901,64941713801,65013124701,65110388901,65186161301,65242431801,64819295301,64959349001,65178344701,65138059401,65381591701,64194037001,64200835001,64199835101,64269647601,64265367501,64282060001,64278034001,64334048201,64360896001,64431710601,64469748601,64543959101,64605917601,64575867801,64640967001,64630233101,64690644101,64730716001,64724839701,64737989101,64781015601,64783916401,64842600201,64862737901,65037771001,65067488601,65064963101,65071636801,65105832401,65087498701,65144701001,65228846501,64841237301,64884538801,65012589801,65072756601,65143361101,65162667301,65174755201,65334940401,65311914201,65367982201,64188131501,64237010001,64317964501,64325643601,64428127301,64546473001,64574221601,64626018401,64619559201,64614113101,64669526701,64720267501,64723830401,64740970601,64773201701,64817436001,64825843301,64855876601,64909745901,64922521201,64986554401,65075053801,65105794401,65096382801,65181989401,65309776201,65377829001,64834885301,64814920301,64982235801,65017720701,65066084401,65224223701,65283647001,65304143101,65388233701,64198070801,64237352901,64343340801,64369141301,64522060001,64577386701,64596292801,64643175901,64637651301,64618132201,64649455401,64729252001,64735351101,64727640101,64749962801,64786193501,64805295001,64768843901,64810466801,64818850301,64844457901,64845156401,64902097201,64932771801,65019785801,65004092501,65112480601,64823930801,64844631701,64809036101,64824197001,64836686401,64865284201,64869993301,64979323801,65031281901,65125050301,65196291801,65214582601,65309464601,64115216901,64234293001,64234353501,64238422401,64304030901,64301201601,64519508701,64544598601,64634291601,64713778601,64738502201,64748947801,64758691701,64746293601,64770739001,64748985201,64775686601,64802305501,64797897801,64789623601,64820013301,64815838801,64900758801,65005240101,65145351501,65237181801,65301841901,65316226601,65348689801,65356234701,65378890301,64908484201,65001071101,65039511701,65044719301,65073618501,65091778401,65065903101,65125397501,65226235601,65383725001,64124372301,64297186701,64324289601,64316740301,64330691601,64361073601,64369104701,64537374901,64544265401,64538045901,64633256501,64624899801,64736481501,64746451901,64747935901,64755562201,64810510501,64803899501,64768477301,64825460501,64837128301,64856140601,64898673301,64906378701,65009040601,65085190401,65183090401,65226039901,64842094701,64834154901,64835251601,64856242901,64894051101,64968702601,64969150201,64996133301,65027042201,65137345801,65144790601,65167295501,65361195801,64110326101,64105857401,64194425501,64190986901,64235622201,64237215001,64241806901,64296227201,64313855301,64486954601,64490139601,64486725801,64560649601,64588526401,64575388001,64576408201,64586835101,64640509601,64642446201,64651047101,64660313501,64672452001,64649909101,64739292801,64760401401,64774757501,64787801301,64892364001,64901819601,64915609201,64952866201,64986527301,64993542001,65024855701,65099340001,65161538901,65163036601,65208209201,65283900701,65355486001,64871105401,64892786401,64952545001,65121876801,65139128101,65143613901,65283348501,65310707701,65296925801,65334169101,64211225001,64232689301,64233621001,64244315201,64244335001,64340299001,64332757401,64352402301,64381277301,64370885601,64471901901,64541028101,64547829401,64558826101,64600658001,64583934701,64613707301,64671325301,64660559601,64712807401,64792722401,64797657001,64780847901,64797878101,64987519201,65038910101,65004603301,65087599401,65117804301,65181587801,65171995801,64817092801,64997849301,65049605401,65072500801,65081884901,65156500101,65182445801,65166763101,65228516001,65310613401,65395503701,64105985301,64249911501,64274489901,64283925601,64300322301,64333764501,64473697701,64494038901,64550428101,64554741301,64538229201,64545234001,64544839001,64600649801,64612809101,64650224601,64636008201,64622114101,64680475701,64689007301,64774116501,64758713201,64800657301,64804627201,64830035001,64891653701,64889989401,64923805601,65034945101,65029776301,65046891201,65071745601,65118599101,64817859101,64951535501,64993870101,64986525401,64985835701,65012332501,65015882201,65100387501,65084913701,65139312101,65171153801,65295031401,64105769201,64263114101,64275406601,64296599201,64354743201,64380743701,64469407201,64520937801,64530936601,64570303601,64593534301,64601817501,64621737401,64661835501,64691358401,64733947101,64744817501,64782921501,64792029301,64830205001,64825828101,64880885301,64867506001,64996615001,64972869501,65030821701,65007615001,65100999701,65085212301,65183525901,65148327201,65257326201,65341846901,64852804301,64870765301,64876417101,65032561001,65106704301,65095162001,65152420101,65153524501,65137800001,65196644701,65281186301,65282225801,65265779701,65361024101,64111303001,64172256601,64169938901,64227548101,64273254201,64252317901,64269623101,64285467501,64287786301,64284603601,64311855001,64342068001,64340305401,64426818201,64461787201,64468804501,64598958301,64595663101,64650488201,64665924101,64653902201,64732319401,64740568301,64765639501,64773623301,64780309801,64794357201,64910659101,65033614901,65029867201,65016305301,65055517701,65082760701,65164462101,65222824401,65216349101,65296577201,65337936101,64994874001,65079105401,65096332501,65088343201,65143594001,65160303701,65180277901,65195116501,65333856601,65295313701,65299005301,64198958501,64188467501,64236700301,64244334301,64254129001,64260640701,64261760501,64271327501,64294014701,64300222001,64304889301,64292900701,64331635001,64381168001,64469747401,64540565401,64526090701,64561357601,64561920801,64538911001,64634655001,64613825401,64660848501,64671536901,64664898401,64773950201,64745969501,64770761301,64788907201,64801746101,64995522101,65058459701,65071431201,65063815901,65155582001,65236859901,65279406801,65301818501,65366736101,64843948701,64956505101,64993148301,65053894701,65132407501,65175117201,65177156301,65188448901,65138985201,65270118701,65314169001,64238574701,64328433701,64502144601,64557083001,64576292301,64606601901,64577020501,64628584101,64613020401,64667440501,64700455901,64736199501,64727331101,64717111201,64738831601,64749079401,64777244701,64829516701,64845013501,64845157601,64903982501,64982020901,65036032901,65039031501,65206132501,65326001901,65326188901,64818856801,64915995601,64898158901,64947466401,65106023901,65145273901,65218851801,65267482801,65335161401,65391529001,64142178701,64210520601,64334521401,64341224801,64380987301,64381207301,64495607301,64579526601,64575326801,64622306701,64639846201,64650470001,64651882401,64647149101,64697558801,64774363001,64753853501,64792417001,64787586101,64812511001,64786205601,64841266101,64841684101,64841038401,64854433101,65000925301,65033780401,65113595301,65119401101,65159633601,64822447301,64876967701,64892017901,64972885501,65035507101,65050125001,65161666101,64153196901,64305382501,64328165101,64331143901,64329315701,64416116701,64433540701,64474792901,64526679901,64517231501,64539713801,64549624801,64549136401,64557163101,64538276701,64629751001,64626076501,64653374601,64714398201,64730580301,64798387601,64855341801,64903174201,64929457601,64934730401,65019331401,64838078401,64827389701,64851180501,64868420801,64919725901,64976550201,65119230701,65151707201,65206683401,65319236501,64180636401,64198618101,64268570001,64285033701,64426756601,64426599301,64522436801,64519304001,64516360701,64534678701,64568203401,64537183701,64538131901,64612324401,64625831301,64624513501,64674215901,64696132301,64707672701,64717718701,64747621101,64796660201,64845679501,64858409801,64899913901,64920877701,65014378801,65015586601,65159259801,65298260201,65137156401,65217124201,65218856701,65318691901,64097151801,64227422601,64242160401,64302084001,64327893301,64342090701,64345254601,64331262601,64404268501,64555771501,64545372901,64598186401,64586062201,64629801501,64618256701,64639397601,64645461101,64661150401,64678444601,64721796701,64755961601,64804342601,64811530301,64832230101,64809621501,64848799401,64924144701,64915675801,64939769401,64975692101,65104263401,65167296701,64854281501,64932372701,64929000101,64984472301,65096903101,65173181701,65226373301,65357371201,65392748101";
//        String[] recordArr = str.split(",");
//        System.out.println(JSON.toJSONString(recordArr));
//        ArrayList<String> notExistsList = new ArrayList<>();
//        for (int index = 0; index < recordArr.length; index++) {
//            String recordId = recordArr[index];
//            if (!recordIdList.contains(recordId)) {
//                notExistsList.add(recordId);
//            }
//        }
//        System.out.println(notExistsList.size());
//        System.out.println(notExistsList);
//        String filePath = "/Users/wangyang/Downloads/DEDUCTION_20200411.txt";
//        FileUtil.writeLines(filePath, notExistsList);
//        System.out.println();
    }
}
