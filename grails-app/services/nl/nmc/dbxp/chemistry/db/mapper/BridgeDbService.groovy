package nl.nmc.dbxp.chemistry.db.mapper

import org.bridgedb.*
import org.bridgedb.bio.*

class BridgeDbService {

    static transactional = true

    def example() {
		
		//BioDataSource.init()
		
		Class.forName("org.bridgedb.file.IDMapperText");
//		def mapper = BridgeDb.connect("idmapper-text:file:///Users/miv/Documents/workspace-sts/dbxpChemistry/dbs/compounds_demo.txt");
//		def src = new Xref ("P07215", DataSource.getByFullName("UniProt/SwissProt Accession") );
		def mapper = BridgeDb.connect("idmapper-text:file:///Users/miv/Documents/workspace-sts/dbxpChemistry/dbs/metabolites.txt");
		def src = new Xref ("3333", DataSource.getByFullName("Pubchem") );
		
		mapper.mapID(src).each { dest ->
				println (" >> " + dest.dump());
		}
		println " ..... "
		println ""
		

//		Class.forName("org.bridgedb.webservice.bridgerest.BridgeRest")
//		def mapper = BridgeDb.connect("idmapper-bridgerest:http://webservice.bridgedb.org/Human")
//		def src = new Xref('17634', BioDataSource.CHEBI) // Glucose
//		mapper.mapID(src, BioDataSource.PUBCHEM).each { dest ->
//			println dest.getId()
//		}
				
		return "Done!"
    }
}
