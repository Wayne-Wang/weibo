package Common;
/*                                                                                                                                 
 * The Open Source Portfolio Initiative Software is Licensed under the Educational Community License Version 1.0:                  
 *                                                                                                                                 
 * This Educational Community License (the "License") applies to any original work of authorship                                   
 * (the "Original Work") whose owner (the "Licensor") has placed the following notice immediately                                  
 * following the copyright notice for the Original Work:                                                                           
 *                                                                                                                                 
 * Copyright (c) 2004 Trustees of Indiana University and r-smart Corporation                                                       
 *                                                                                                                                 
 * This Original Work, including software, source code, documents, or other related items, is being                               
 * provided by the copyright holder(s) subject to the terms of the Educational Community License.                                 
 * By obtaining, using and/or copying this Original Work, you agree that you have read, understand,                               
 * and will comply with the following terms and conditions of the Educational Community License:                                  
 *                                                                                                                                
 * Permission to use, copy, modify, merge, publish, distribute, and sublicense this Original Work and                             
 * its documentation, with or without modification, for any purpose, and without fee or royalty to the                            
 * copyright holder(s) is hereby granted, provided that you include the following on ALL copies of the                            
 * Original Work or portions thereof, including modifications or derivatives, that you make:                                      
 *                                                                                                                                
 * - The full text of the Educational Community License in a location viewable to users of the                                    
 * redistributed or derivative work.                                                                                              
 *                                                                                                                                
 * - Any pre-existing intellectual property disclaimers, notices, or terms and conditions.                                        
 *                                                                                                                                
 * - Notice of any changes or modifications to the Original Work, including the date the changes were made.                       
 *                                                                                                                                
 * - Any modifications of the Original Work must be distributed in such a manner as to avoid any confusion                        
 *  with the Original Work of the copyright holders.                                                                              
 *                                                                                                                                
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT                              
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.                        
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,                        
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE                            
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.                                                                         
 *                                                                                                                                
 * The name and trademarks of copyright holder(s) may NOT be used in advertising or publicity pertaining                          
 * to the Original or Derivative Works without specific, written prior permission. Title to copyright                             
 * in the Original Work and any associated documentation will at all times remain with the copyright holders.                     
 *                                                                                                                                
 * $Header: /CVSdata/bnms_workstation/src/com/sitech/ismp/util/RandomGUID.java,v 1.1 2009/01/13 08:04:57 yanggq Exp $  
 * $Revision: 1.1 $                                                                                                               
 * $Date: 2009/01/13 08:04:57 $                                                                                                   
 */

/*
 * RandomGUID
 * 
 * @version 1.2.1 11/05/02 @author Marc A. Mnich
 * 
 * From www.JavaExchange.com, Open Software licensing
 * 
 * 11/05/02 -- Performance enhancement from Mike Dubman. Moved InetAddr.getLocal
 * to static block. Mike has measured a 10 fold improvement in run time.
 * 01/29/02 -- Bug fix: Improper seeding of nonsecure Random object caused
 * duplicate GUIDs to be produced. Random object is now only created once per
 * JVM. 01/19/02 -- Modified random seeding and added new constructor to allow
 * secure random feature. 01/14/02 -- Added random function seeding with JVM run
 * time
 * 
 */

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/*
 * In the multitude of java GUID generators, I found none that guaranteed
 * randomness. GUIDs are guaranteed to be globally unique by using ethernet
 * MACs, IP addresses, time elements, and sequential numbers. GUIDs are not
 * expected to be random and most often are easy/possible to guess given a
 * sample from a given generator. SQL Server, for example generates GUID that
 * are unique but sequencial within a given instance.
 * 
 * GUIDs can be used as security devices to hide things such as files within a
 * filesystem where listings are unavailable (e.g. files that are served up from
 * a Web server with indexing turned off). This may be desireable in cases where
 * standard authentication is not appropriate. In this scenario, the RandomGUIDs
 * are used as directories. Another example is the use of GUIDs for primary keys
 * in a database where you want to ensure that the keys are secret. Random GUIDs
 * can then be used in a URL to prevent hackers (or users) from accessing
 * records by guessing or simply by incrementing sequential numbers.
 * 
 * There are many other possiblities of using GUIDs in the realm of security and
 * encryption where the element of randomness is important. This class was
 * written for these purposes but can also be used as a general purpose GUID
 * generator as well.
 * 
 * RandomGUID generates truly random GUIDs by using the system's IP address
 * (name/IP), system time in milliseconds (as an integer), and a very large
 * random number joined together in a single String that is passed through an
 * MD5 hash. The IP address and system time make the MD5 seed globally unique
 * and the random number guarantees that the generated GUIDs will have no
 * discernable pattern and cannot be guessed given any number of previously
 * generated GUIDs. It is generally not possible to access the seed information
 * (IP, time, random number) from the resulting GUIDs as the MD5 hash algorithm
 * provides one way encryption.
 * 
 * ----> Security of RandomGUID: <----- RandomGUID can be called one of two ways --
 * with the basic java Random number generator or a cryptographically strong
 * random generator (SecureRandom). The choice is offered because the secure
 * random generator takes about 3.5 times longer to generate its random numbers
 * and this performance hit may not be worth the added security especially
 * considering the basic generator is seeded with a cryptographically strong
 * random seed.
 * 
 * Seeding the basic generator in this way effectively decouples the random
 * numbers from the time component making it virtually impossible to predict the
 * random number component even if one had absolute knowledge of the System
 * time. Thanks to Ashutosh Narhari for the suggestion of using the static
 * method to prime the basic random generator.
 * 
 * Using the secure random option, this class compies with the statistical
 * random number generator tests specified in FIPS 140-2, Security Requirements
 * for Cryptographic Modules, secition 4.9.1.
 * 
 * I converted all the pieces of the seed to a String before handing it over to
 * the MD5 hash so that you could print it out to make sure it contains the data
 * you expect to see and to give a nice warm fuzzy. If you need better
 * performance, you may want to stick to byte[] arrays.
 * 
 * I believe that it is important that the algorithm for generating random GUIDs
 * be open for inspection and modification. This class is free for all uses.
 *  - Marc
 */

public class RandomGUID extends Object {
	// protected final org.apache.commons.logging.Log logger =
	// org.apache.commons.logging.LogFactory.getLog(getClass());

	public String valueBeforeMD5 = "";
	public String valueAfterMD5 = "";
	private static Random myRand;
	private static Random random;
	private static SecureRandom mySecureRand;

	private static String s_id;
	private static final int PAD_BELOW = 0x10;
	private static final int TWO_BYTES = 0xFF;

	/*
	 * Static block to take care of one time secureRandom seed. It takes a few
	 * seconds to initialize SecureRandom. You might want to consider removing
	 * this static block or replacing it with a "time since first loaded" seed
	 * to reduce this time. This block will run only once per JVM instance.
	 */

	static {
		mySecureRand = new SecureRandom();
		long secureInitializer = mySecureRand.nextLong();
		myRand = new Random(secureInitializer);
		random = new Random();
		try {
			s_id = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Default constructor. With no specification of security option, this
	 * constructor defaults to lower security, high performance.
	 */
	public RandomGUID() {
		getRandomGUID(false);
	}

	/*
	 * Constructor with security option. Setting secure true enables each random
	 * number generated to be cryptographically strong. Secure false defaults to
	 * the standard Random function seeded with a single cryptographically
	 * strong random number.
	 */
	public RandomGUID(boolean secure) {
		getRandomGUID(secure);
	}

	/*
	 * Method to generate the random GUID
	 */
	private void getRandomGUID(boolean secure) {
		MessageDigest md5 = null;
		StringBuffer sbValueBeforeMD5 = new StringBuffer(128);

		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		try {
			long time = System.nanoTime();
			long rand = 0;

			if (secure) {
				rand = mySecureRand.nextLong();
			} else {
				rand = myRand.nextLong();
			}

			// This StringBuffer can be a long as you need; the MD5
			// hash will always return 128 bits. You can change
			// the seed to include anything you want here.
			// You could even stream a file through the MD5 making
			// the odds of guessing it at least as great as that
			// of guessing the contents of the file!
			sbValueBeforeMD5.append(s_id);
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(time));
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(rand));

			valueBeforeMD5 = sbValueBeforeMD5.toString();
			md5.update(valueBeforeMD5.getBytes());

			byte[] array = md5.digest();
			StringBuffer sb = new StringBuffer(32);
			for (int j = 0; j < array.length; ++j) {
				int b = array[j] & TWO_BYTES;
				if (b < PAD_BELOW) {
					sb.append('0');
				}
				sb.append(Integer.toHexString(b));
			}

			valueAfterMD5 = sb.toString();

		} catch (Exception e) {
			  e.printStackTrace();
		}
	}

	/*
	 * Convert to the standard format for GUID (Useful for SQL Server
	 * UniqueIdentifiers, etc.) Example: C2FEEEAC-CFCD-11D1-8B05-00600806D9B6
	 */
	@Override
	public String toString() {
		String raw = valueAfterMD5.toUpperCase();
		StringBuffer sb = new StringBuffer(64);
		sb.append(raw.substring(0, 8));
		// sb.append("-");
		sb.append(raw.substring(8, 12));
		// sb.append("-");
		sb.append(raw.substring(12, 16));
		// sb.append("-");
		sb.append(raw.substring(16, 20));
		// sb.append("-");
		sb.append(raw.substring(20));
		
		return sb.toString();
	}

	public static String getRandomGUID() {
		RandomGUID myGUID = new RandomGUID();
		return myGUID.toString();
	}
	
	//生成随机数字和字母, 字母区分大小写 
    public static String getStringRandom(int length) {  
          
        String val = "";
          
        //参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) {  
              
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }  
      
}