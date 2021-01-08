package Common;

import com.alibaba.fastjson.JSON;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wayne
 * @Classname Test
 * @Description TODO
 * @Date 20/11/10 上午12:14
 * @Version V1.0
 */
public class Test {

//    public static void main(String[] args) {
//        String str = "Delivering parcels and letters isn't always straightforward in Ivory Coast, where street addresses are few and far between, and large settlements often have no address system at all.  The postal service currently relies heavily on Post Office mailboxes and descriptive directions that mention visible landmarks, but for many customers, home deliveries are not an option. To tackle the problem, the country has adopted a new system for its postal addresses, which replaces street names and building numbers with three-word phrases. Designed to support the country's rising e-commerce sector, the system is provided by what3words, a UK startup that has divided the world's surface into a grid of 3m by 3m squares, assigning a unique three-word address to each one. Looks.cherry.humans? The what3words algorithm turns lengthy GPS coordinates into just three words, taken from a list of up to 40,000 vocabulary items in 14 languages, filtered for homophones -- for example, \"sail\" and \"sale\" -- and offensive terms.  Because the algorithm doesn't need to store all the information, it requires no internet connection and is only 10 megabytes in size, which makes it flexible for most smartphones and devices. Users can type any address into the app or the website and get directions to it, or see it on a satellite map. Conversely, they can pinpoint to any specific location, such as their front door, and get a three-word address for it. This way, any location on the planet can be identified by a simple phrase like looks.cherry.humans (the entrance to the White House) or input.caring.brain (10 Downing Street in London, the residence of the UK's Prime Minister). Each address points to one of 57 trillion individual squares, three meters in diameter, on the Earth's surface.  \"We wanted to make the squares as small as possible, but not too small,\" Chris Sheldrick, CEO and Co-Founder of what3words, told CNN.  \"There was no point in going any more granular than three meters, because that's the maximum accuracy GPS can reach on a smartphone anyway.\"   Built-in error correction The company launched in 2013, to address a problem Sheldrick was facing during his time in the music and events business. \"I was getting getting constant frustration from trying to coordinate 50 people to all turn up at the same place, be it a stadium with multiple entrances, or a villa on a mountain side, or a specific house in Nigeria,\" he said. \"Addresses are not always fit for that purpose, they work well in places like New York and London, but not so much around the world,\" he added.  According to what3words, around 75% of the world -- or 135 countries -- suffers from inconsistent, complicated or inadequate addressing systems. For ease of use, the most common words such as \"chair\" are reserved for densely populated areas, whereas obscure terms such as \"dodecahedron,\" are confined to remote locations like Antarctica. Furthermore, similar addresses such as table.chair.spoon and table.chair.spoons are nowhere near each other, to help users recognize spelling mistakes.  Languages are parsed automatically and, according to the company, there is \"100% certainty that all instances of the system running everywhere in the world will provide the same 3 word address for the same location.\" In Mongolia's footsteps Mongolia was the first country to adopt what3words for its postal address system, in 2016. \"We talked to postal services that were facing a challenge,\" said Sheldrick. \"Mongolia and Ivory Coast are trying to develop their e-commerce market, but can't get deliveries done because their addresses are not straightforward. Even though they are developing a better long term plan, this takes time, and they needed something that works now.\" With a population of 22.7 million -- six times that of Mongolia -- and a total land area of 322,463 square kilometers, Ivory Coast is a large country: if it were a US state, if would be the fifth largest, after Montana and ahead of New Mexico. Its economy was the fastest growing in Africa in 2016, according to the International Monetary Fund (IMF). Its GDP is predicted to increase by an average of 7.4 percent between 2017 and 2020. The country's national post system, La Poste, will integrate what3words in a free app that allows users to look up any address with a smartphone and write three words on an envelope. Major e-commerce retailers such as Yaatoo will also accept the addresses on their checkout pages, and official state documents will be available for home delivery using Document.ci. \"In what3words, La Poste has found a simple solution that instantly provides Côte d'Ivoire with a robust and multi-lingual addressing system. It will help us to extend e-commerce opportunities, home delivery and support businesses in both urban and rural spaces,\" Isaac Gnamba-Yao, CEO of La Poste de Côte d'Ivoire, said in a statement.  Closed platform The service is free for end users, but not for businesses or national postal services.  \"We license a bit of software to postal services and other business who want to convert to what3words and do that in bulk,\" said Sheldrick. The algorithm is protected by a patent, which makes what3words a closed, proprietary system unlike some other open-source geo-location platforms. The addresses, unlike traditional ones, also do not suggest any sort of geographical location until they are decoded by the app.  Despite these shortcomings, what3words has already been adopted by over 400 businesses and institutions, according to Sheldrick. Among them are the United Nations, which integrated it in an app used for disaster and humanitarian reporting, and the Glastonbury festival, where it was used to track down people in need of medical attention. The app is also used in several African countries other than Ivory Coast: malaria prevention in East Africa, food deliveries in Ethiopia, and fiber optics projects in Kenya are among the examples. Further expansion is based on language rather than countries, explains Sheldrick: \"With English, French and Swahili covered, most African countries should be able to use the service.";
//        String[] arr = str.split("\\.");
//        System.out.println(arr.length);
//    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("userProvince", "34");
        map.put("userCity", "340");
        map.put("userNetwork", "2");
        map.put("accType", "2");
        System.out.println(ObjectSizeCalculator.getObjectSize(map));

        byte[] arr = JSON.toJSONString(map).getBytes();
        System.out.println(arr.length);
        int a = 4;
        System.out.println(ObjectSizeCalculator.getObjectSize(a));
    }
}
