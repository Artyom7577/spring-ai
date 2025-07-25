//package com.polixis.springai.rag.config;
//
//import java.io.File;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.ai.document.Document;
//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.ai.reader.tika.TikaDocumentReader;
//import org.springframework.ai.transformer.splitter.TextSplitter;
//import org.springframework.ai.transformer.splitter.TokenTextSplitter;
//import org.springframework.ai.vectorstore.SimpleVectorStore;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class VectorStoreConfig {
//
//    private static final Logger log = LoggerFactory.getLogger(VectorStoreConfig.class);
//
//    @Bean
//    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel, VectorStoreProperties vectorStoreProperties) {
//        var store = SimpleVectorStore.builder(embeddingModel).build();
//        File vectorStoreFile = new File(vectorStoreProperties.getVectorStorePath());
//
//        if (vectorStoreFile.exists()) {
//            store.load(vectorStoreFile);
//        } else {
//            log.warn("Vector store file {} does not exist", vectorStoreFile.getAbsolutePath());
//            vectorStoreProperties.getDocumentsToLoad().forEach(document ->  {
//                log.info("Loading document: {}", document.getFilename());
//                TikaDocumentReader documentReader = new TikaDocumentReader(document);
//                List<Document> documents = documentReader.get();
//                TextSplitter textSplitter = new TokenTextSplitter();
//                List<Document> splitDocs = textSplitter.split(documents);
//                store.add(splitDocs);
//            });
//
//            log.info("Saving vector store to {}", vectorStoreFile.getAbsolutePath());
//
//            store.save(vectorStoreFile);
//        }
//
//        return store;
//    }
//
//}
