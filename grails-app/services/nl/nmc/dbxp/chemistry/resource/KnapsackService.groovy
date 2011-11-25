package nl.nmc.dbxp.chemistry.resource

import nl.nmc.dbxp.chemistry.ResourceService

/**
 * A Grails Service to provide easy access to the Knapsack number
 *
 * @since	November - 2011
 * @author	Michael van Vliet
 * @email	m.s.vanvliet@lacdr.leidenuniv.nl
 */
class KnapsackService extends ResourceService {

	static transactional = true

	/**
	 * Defines class used in BridgeDB to be able to look for identical resource keys
	 */
	String bridgeDbClass = 'Knapsack'

	/**
	 * @param knapsackId e.g C00000257
	 * @return http://kanaya.naist.jp/knapsack_jsp/information.jsp?word=C00000257
	 */
	String knapsackUrlByKnapsackId(String knapsackId = '') {
		return ('http://kanaya.naist.jp/knapsack_jsp/information.jsp?word=' + knapsackId) as String
	}
	
	/**
	* @param word e.g Lycopene
	* @return http://kanaya.naist.jp/knapsack_jsp/result.jsp?sname=all&word=Lycopene
	*/
   String knapsackSearchUrlByWord(String word = '') {
	   return ('http://kanaya.naist.jp/knapsack_jsp/result.jsp?sname=all&word=' + word) as String
   }
}