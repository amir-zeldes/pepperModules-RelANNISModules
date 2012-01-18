/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.tests;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfaceFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.moduleTests.PepperExporterTest;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.moduleTests.PepperModuleTestException;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.RelANNISExporter;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusDocumentRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltSample.SaltSample;


public class RelANNISExporterTest extends PepperExporterTest
{
	URI resourceURI= URI.createFileURI(new File(".").getAbsolutePath());
	
	protected void setUp()
	{
		super.setFixture(new RelANNISExporter());
		super.getFixture().setSaltProject(SaltFactory.eINSTANCE.createSaltProject());
		super.setResourcesURI(resourceURI);
		URI temporaryURI= URI.createFileURI("_TMP/de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.exmaralda");
		//System.out.println("Exporter Temp-URI: "+new File(temporaryURI.toFileString()).getAbsolutePath());
		super.setTemprorariesURI(temporaryURI);
		
		//set formats to support
		FormatDefinition formatDef= PepperInterfaceFactory.eINSTANCE.createFormatDefinition();
		formatDef.setFormatName("RelANNIS");
		formatDef.setFormatVersion("3.1");
		this.supportedFormatsCheck.add(formatDef);
		
		// set corpus definition
		CorpusDefinition corpDef= PepperInterfaceFactory.eINSTANCE.createCorpusDefinition();
		corpDef.setFormatDefinition(formatDef);
	}
	
	public void testSetGetCorpusDefinition()
	{
		//TODO something to test???
		CorpusDefinition corpDef= PepperInterfaceFactory.eINSTANCE.createCorpusDefinition();
		FormatDefinition formatDef= PepperInterfaceFactory.eINSTANCE.createFormatDefinition();
		formatDef.setFormatName("RelANNIS");
		formatDef.setFormatVersion("3.1");
		corpDef.setFormatDefinition(formatDef);
	}

	private void removeDirRec(File dir)
	{
		if (dir != null)
		{
			if (dir.listFiles()!= null && dir.listFiles().length!= 0)
			{	
				for (File subDir: dir.listFiles())
				{
					this.removeDirRec(subDir);
				}
			}
			dir.delete();
		}
	}
	
	/**
	 * Tests exporting a corpus structure. 
	 * 		corp1
	 *		|
	 *		doc1
	 * Tests importing a corpus with one document and without a timeline. The timeline has to be computed.
	 * This test checks the sample of {@link SaltSample} created by the methods {@link SaltSample#createPrimaryData(SDocument)},
	 * {@link SaltSample#createTokens(SDocument)}, {@link SaltSample#createMorphologyAnnotations(SDocument)}, {@link SaltSample#createInformationStructureSpan(SDocument)}
	 * and {@link SaltSample#createInformationStructureAnnotations(SDocument)}.  
	 * @throws IOException 
	 */
	public void testStart1() throws IOException
	{
		File corpusPathFile= new File("./_TMP/de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.relannis/current");
		//File currentFile = new File("./_TMP/de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.exmaralda/current/corp1/doc1.exb");
		//File expectedFile= new File("./src/test/resources/EXMARaLDAExporter/expected/sample1/corp1/doc1.exb");
		
		URI corpusPath= URI.createFileURI(corpusPathFile.getCanonicalPath());
		//URI currentURI = URI.createFileURI(currentFile.getCanonicalPath());
		//URI expectedURI= URI.createFileURI(expectedFile.getCanonicalPath());
		
//		if (new File(expectedURI.toFileString()).exists()){
//			System.out.println(new File(expectedURI.toFileString()).getAbsolutePath() + " exists");
//		} else {
//			throw new PepperModuleTestException(expectedFile.getAbsolutePath() + " does not exist");
//		}
		
		this.removeDirRec(new File(corpusPath.toFileString()));
		
		{//creating and setting corpus definition
			CorpusDefinition corpDef= PepperInterfaceFactory.eINSTANCE.createCorpusDefinition();
			FormatDefinition formatDef= PepperInterfaceFactory.eINSTANCE.createFormatDefinition();
			formatDef.setFormatName("RelANNIS");
			formatDef.setFormatVersion("3.1");
			corpDef.setFormatDefinition(formatDef);
			corpDef.setCorpusPath(corpusPath);
			this.getFixture().setCorpusDefinition(corpDef);
		}
		
		
		//start: create sample
			//start:create corpus structure
				SDocument sDoc= this.createCorpusStructure();
			//end:create corpus structure
			SaltSample.createPrimaryData(sDoc);
			SaltSample.createTokens(sDoc);
			SaltSample.createMorphologyAnnotations(sDoc);
			SaltSample.createInformationStructureSpan(sDoc);
			SaltSample.createInformationStructureAnnotations(sDoc);
		//end: create sample
		
		//start: exporting document
			this.start();
		//end: exporting document
		
		{//checking if export was correct
			//assertTrue("The files '"+expectedURI+"' and '"+currentURI+"' aren't identical. ", this.compareFiles(expectedURI, currentURI));
		}
	}
	

	
	/**
	 * Creates a corpus structure with one corpus and one document. It returns the created document.
	 * 		corp1
	 *		|
	 *		doc1
	 * @return
	 */
	private SDocument createCorpusStructure()
	{
		{//creating corpus structure
			SCorpusGraph corpGraph= SaltFactory.eINSTANCE.createSCorpusGraph();
			this.getFixture().getSaltProject().getSCorpusGraphs().add(corpGraph);
			//		corp1
			//		|
			//		doc1
			
			//corp1
			SElementId sElementId= SaltFactory.eINSTANCE.createSElementId();
			sElementId.setSId("corp1");
			SCorpus corp1= SaltFactory.eINSTANCE.createSCorpus();
			corp1.setSName("corp1");
			corp1.setSElementId(sElementId);
			corpGraph.addSNode(corp1);
			
			//doc1
			SDocument doc1= SaltFactory.eINSTANCE.createSDocument();
			sElementId= SaltFactory.eINSTANCE.createSElementId();
			sElementId.setSId("corp1/doc1");
			doc1.setSElementId(sElementId);
			doc1.setSName("doc1");
			corpGraph.addSNode(doc1);
			doc1.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
			
			//CorpDocRel
			SCorpusDocumentRelation corpDocRel1= SaltFactory.eINSTANCE.createSCorpusDocumentRelation();
			sElementId= SaltFactory.eINSTANCE.createSElementId();
			sElementId.setSId("rel1");
			corpDocRel1.setSElementId(sElementId);
			corpDocRel1.setSName("rel1");
			corpDocRel1.setSCorpus(corp1);
			corpDocRel1.setSDocument(doc1);
			corpGraph.addSRelation(corpDocRel1);
			return(doc1);
		}
	}
}
