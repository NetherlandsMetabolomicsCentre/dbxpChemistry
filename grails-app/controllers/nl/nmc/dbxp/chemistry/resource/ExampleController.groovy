package nl.nmc.dbxp.chemistry.resource

import org.springframework.context.ApplicationContext
import org.codehaus.groovy.grails.commons.ApplicationHolder

class ExampleController {

	def ChebiService
	def ChemspiderService
	def PubchemService
	def LipidmapsService
	def HmdbService
	def KeggService
	def CasService
	def KnapsackService
	def BridgeDbService

	def chebi = {
		render ChebiService.findAllChebiByLabel('glucose' as String, ['max':3,'synonyms':true,'ontologychildren':true,'ontologyparents':true,'ontologychildreninpath':true])
	}

	def chebies = {

		['glucose', '*amino'].each { label ->
			def chebies = ChebiService.findAllChebiByLabel(label as String, ['max': 5, 'details':false])
			chebies.each { chebiID, chebiMeta ->
				render "<h3>${chebiID} - ${chebiMeta.asciiName} (${chebiMeta.searchScore}/${chebiMeta.entityStar})</h3>"

				ChebiService.findAllSynonymsChebiById(chebiID).each { synonym, synonymMeta ->
					render "<b>${synonym}</b> :: ${synonymMeta?.collect { it.value }.join(', ')}<br />"
				}

				ChebiService.findAllOntologyChildrenByChebiId(chebiID).each { ontologyChildChebiId, ontologyChildChebiMeta ->
					render "<b>${ontologyChildChebiId}</b> :: ${ontologyChildChebiMeta?.collect { it.value }.join(', ')}<br />"
				}


				ChebiService.findAllOntologyParentsByChebiId(chebiID).each { ontologyParentChebiId, ontologyParentChebiMeta ->
					render "<b>${ontologyParentChebiId}</b> :: ${ontologyParentChebiMeta?.collect { it.value }.join(', ')}<br />"
				}

				ChebiService.findAllOntologyChildrenInPathByChebiId(chebiID).each { ontologyInPathChebiId, ontologyInPathChebiMeta ->
					render "<b>${ontologyInPathChebiId}</b> :: ${ontologyInPathChebiMeta.asciiName} (${ontologyInPathChebiMeta.searchScore}/${ontologyInPathChebiMeta.entityStar})<br />"
				}
			}
		}
	}

	def chemspider = {

		render "Identical Resource Identifiers :: <p>${	ChemspiderService.identicalResourceKeys('CHEMSPIDER:0001')	}</p>"

		try {

			//def inchi = 'InChI=1S/C18H32O3/c1-2-3-4-5-7-10-13-16-17(21-16)14-11-8-6-9-12-15-18(19)20/h7,10,16-17H,2-6,8-9,11-15H2,1H3,(H,19,20)/b10-7-'
			def inchi = 'InChI=1S/C27H30O16/c1-8-17(32)20(35)22(37)26(40-8)39-7-15-18(33)21(36)23(38)27(42-15)43-25-19(34)16-13(31)5-10(28)6-14(16)41-24(25)9-2-3-11(29)12(30)4-9/h2-6,8,15,17-18,20-23,26-33,35-38H,7H2,1H3/t8-,15+,17-,18+,20+,21-,22+,23+,26+,27-/m0/s1'

			def inchiKey 		= ChemspiderService.inchiToInchikey(inchi)
			def chemspiderId 	= ChemspiderService.inchiKeyToChemspiderId(inchiKey)
			def compoundName 	= ChemspiderService.chemspiderIdToCompoundName(chemspiderId)
			def chemspiderURL	= ChemspiderService.chemspiderUrlByChemspiderId(chemspiderId)

			render "I :: ${inchi} <br />"
			render "IK :: ${inchiKey} <br />"
			render "CID :: ${chemspiderId} <br />"
			render "CNAME :: ${compoundName} <br />"
			render "CURL :: <a target=\"_blank\" href=\"${chemspiderURL}\">${chemspiderURL}</a><br />"
		} catch (e) {
			log.error e
		}
		render "done!"
	}

	def pubchem = {

		render PubchemService.pubchemSearchPageByInchiKey('IKGXIBQEEMLURG-NVPNHPEKSA-N')
		render PubchemService.identicalResourceKeys('PC:0001')
	}

	def lipidmaps = {

		//returns URL of LipidMaps page
		render LipidmapsService.lipidmapsUrlByLMID('LIPIDMAPS:0001')

		//returns MOL data of Lipid
		render LipidmapsService.lipidmapsMOLDataByLMID('LMSP03010003')
	}

	def hmdb = {
		render HmdbService.hmdbUrlByHmdbId('HMDB00122')
		render HmdbService.identicalResourceKeys('HMDB:0001')
	}

	def kegg = {
		render KeggService.keggUrlByKeggId('C00031')
		render KeggService.identicalResourceKeys('KEGG:0001')
	}

	def cas = {
		render CasService.identicalResourceKeys('CAS:0001')
	}

	def knapsack = {
		render KnapsackService.identicalResourceKeys('KNAPSACK:0001')
	}

	def bridgedb = {

		def localPath = (ApplicationHolder.getApplication().getParentContext().getResource("/").getFile().toString()).replace('/web-app','')

		render "<br />Local txt file 'metabolites.txt'<br />" +
				BridgeDbService.identicalResourceKeys(
				"idmapper-text:file://${localPath}/dbs/metabolites.txt",
				"Pubchem",
				"PC:0001"
				)

		render "<br />Local txt file 'compounds_demo.txt'<br />" +
				BridgeDbService.identicalResourceKeys(
				"idmapper-text:file://${localPath}/dbs/compounds_demo.txt",
				"Ensembl Gene ID",
				"YPR161C"
				)

		try {
			render "<br />http://webservice.bridgedb.org/Human<br />" +
					BridgeDbService.identicalResourceKeys(
					"idmapper-bridgerest:http://webservice.bridgedb.org/Human",
					"Ensembl Gene ID",
					"YPR161C"
					)
		} catch(e) {
			render "<br /><font color=red>Was unable to contact: <b>http://webservice.bridgedb.org/Human</b>. Server may be down.</font>"
		}
	}
}

