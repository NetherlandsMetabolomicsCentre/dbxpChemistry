package nl.nmc.dbxp.chemistry.db.mapper

import org.bridgedb.*
import org.bridgedb.bio.*

class BridgeDbService {

    static transactional = true

    def example() {
		
		def respString = ""
		
		try {
			Class.forName("org.bridgedb.file.IDMapperText");
			
			BridgeDb.connect("idmapper-text:file:///Users/miv/Documents/workspace-sts/dbxpChemistry/dbs/compounds_demo.txt").mapID(
				new Xref ("P07215", DataSource.getByFullName("UniProt/SwissProt Accession"))).each { dest ->
					respString += "<br />UniProt/SwissProt Accession :: P07215 >> ${dest.id} ${dest.ds} ${dest.rep}"
			}
			respString += "<br /> ... "
			respString += "<br />"
				
			BridgeDb.connect("idmapper-text:file:///Users/miv/Documents/workspace-sts/dbxpChemistry/dbs/metabolites.txt").mapID(
				new Xref ("3333", DataSource.getByFullName("Pubchem"))).each { dest ->
					respString += "<br />Pubchem :: 3333 >> ${dest.id} ${dest.ds} ${dest.rep}"
			}
			respString += "<br /> ... "
			respString += "<br />"
			
			BridgeDb.connect("idmapper-text:file:///Users/miv/Documents/workspace-sts/dbxpChemistry/dbs/metabolites.txt").mapID(
				new Xref ("HMDB00122", DataSource.getByFullName("HMDB"))).each { dest ->
					respString += "<br />HMDB :: HMDB00122 >> ${dest.id} ${dest.ds} ${dest.rep}"
			}
			respString += "<br /> ... "
			respString += "<br />"
			
			BioDataSource.init()
			Class.forName("org.bridgedb.webservice.bridgerest.BridgeRest")
			BridgeDb.connect("idmapper-bridgerest:http://webservice.bridgedb.org/Human").mapID(
				new Xref('17634', BioDataSource.CHEBI), BioDataSource.PUBCHEM).each { dest ->
					respString += "<br />Chebi :: 17634 >> Pubchem :: ${dest.id} ${dest.ds} ${dest.rep}"
			}
			respString += "<br /> ... "
			respString += "<br />"
			
			Class.forName("org.bridgedb.webservice.bridgerest.BridgeRest")
			BridgeDb.connect("idmapper-bridgerest:http://webservice.bridgedb.org/Human").mapID(
				new Xref('17634', BioDataSource.CHEBI)).each { dest ->
					respString += "<br />Chebi :: 17634 >> ${dest.id} ${dest.ds} ${dest.rep}"
			}
				
			respString += "<br /> ... done! "
			respString += "<br />"
			
		} catch (e) {
			println e.dump()
		}
		
		return respString
    }
}
