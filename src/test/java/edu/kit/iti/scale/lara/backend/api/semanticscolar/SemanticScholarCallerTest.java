package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import edu.kit.iti.scale.lara.backend.api.HttpMethod;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SemanticScholarCallerTest {




    @Test
    void callPostRequest() throws IOException {

        // set up
        JSONArray positives = new JSONArray().put("649def34f8be52c8b66281af98ae884c09aef38b");
        JSONArray negatives = new JSONArray().put("ArXiv:1805.02262");
        HttpMethod method = HttpMethod.POST;
        String url = "https://api.semanticscholar.org/recommendations/v1/papers/?offset=100&limit=10&fields=url,abstract,authors,title,venue,year,citationCount,referenceCount";

        // expected response
        String expectedResponse = "{\n" +
                "    \"recommendedPapers\": [\n" +
                "        {\n" +
                "            \"paperId\": \"cb92a7f9d9dbcf9145e32fdfa0e70e2a6b828eb1\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/cb92a7f9d9dbcf9145e32fdfa0e70e2a6b828eb1\",\n" +
                "            \"title\": \"The Semantic Scholar Open Data Platform\",\n" +
                "            \"abstract\": \"The volume of scientific output is creating an urgent need for automated tools to help scientists keep up with developments in their field. Semantic Scholar (S2) is an open data platform and website aimed at accelerating science by helping scholars discover and understand scientific literature. We combine public and proprietary data sources using state-of-theart techniques for scholarly PDF content extraction and automatic knowledge graph construction to build the Semantic Scholar Academic Graph, the largest open scientific literature graph to-date, with 200M+ papers, 80M+ authors, 550M+ paper-authorship edges, and 2.4B+ citation edges. The graph includes advanced semantic features such as structurally parsed text, natural language summaries, and vector embeddings. In this paper, we describe the components of the S2 data processing pipeline and the associated APIs offered by the platform. We will update this living document to reflect changes as we add new data offerings and improve existing services.\",\n" +
                "            \"venue\": \"ArXiv\",\n" +
                "            \"year\": 2023,\n" +
                "            \"referenceCount\": 17,\n" +
                "            \"citationCount\": 0,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"143967880\",\n" +
                "                    \"name\": \"Rodney Michael Kinney\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1667818755\",\n" +
                "                    \"name\": \"Chloe Anastasiades\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2202417686\",\n" +
                "                    \"name\": \"Russell Authur\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"46181066\",\n" +
                "                    \"name\": \"Iz Beltagy\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2699105\",\n" +
                "                    \"name\": \"Jonathan Bragg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2202412440\",\n" +
                "                    \"name\": \"Alexandra Buraczynski\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"51199773\",\n" +
                "                    \"name\": \"Isabel Cachola\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2202412446\",\n" +
                "                    \"name\": \"Stefan Candra\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1648642525\",\n" +
                "                    \"name\": \"Yoganand Chandrasekhar\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2527954\",\n" +
                "                    \"name\": \"Arman Cohan\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"46230609\",\n" +
                "                    \"name\": \"Miles Crawford\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"145612610\",\n" +
                "                    \"name\": \"Doug Downey\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2064772563\",\n" +
                "                    \"name\": \"J. Dunkelberger\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1741101\",\n" +
                "                    \"name\": \"Oren Etzioni\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"80530391\",\n" +
                "                    \"name\": \"R. Evans\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"46411828\",\n" +
                "                    \"name\": \"Sergey Feldman\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2202417961\",\n" +
                "                    \"name\": \"Joseph Gorney\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2052859168\",\n" +
                "                    \"name\": \"D. Graham\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2200023814\",\n" +
                "                    \"name\": \"F.Q. Hu\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"153179615\",\n" +
                "                    \"name\": \"Regan Huff\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"145104486\",\n" +
                "                    \"name\": \"Daniel King\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"41018147\",\n" +
                "                    \"name\": \"Sebastian Kohlmeier\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2003338023\",\n" +
                "                    \"name\": \"Bailey Kuehl\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"48758753\",\n" +
                "                    \"name\": \"Michael Langan\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2116442078\",\n" +
                "                    \"name\": \"Daniel Lin\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2143857321\",\n" +
                "                    \"name\": \"Haokun Liu\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"46258841\",\n" +
                "                    \"name\": \"Kyle Lo\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"3047023\",\n" +
                "                    \"name\": \"Jaron Lochner\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2071601396\",\n" +
                "                    \"name\": \"Kelsey MacMillan\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2057256191\",\n" +
                "                    \"name\": \"Tyler Murray\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"145541350\",\n" +
                "                    \"name\": \"Christopher Newell\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2115660042\",\n" +
                "                    \"name\": \"Smita Rao\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"49356109\",\n" +
                "                    \"name\": \"Shaurya Rohatgi\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"114609552\",\n" +
                "                    \"name\": \"P. Sayre\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"101568984\",\n" +
                "                    \"name\": \"Zejiang Shen\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2116287642\",\n" +
                "                    \"name\": \"Amanpreet Singh\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"3328733\",\n" +
                "                    \"name\": \"Luca Soldaini\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"48813613\",\n" +
                "                    \"name\": \"Shivashankar Subramanian\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2125122431\",\n" +
                "                    \"name\": \"A. Tanaka\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1860983\",\n" +
                "                    \"name\": \"Alex D Wade\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"82676859\",\n" +
                "                    \"name\": \"Linda M. Wagner\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"31860505\",\n" +
                "                    \"name\": \"Lucy Lu Wang\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"46212260\",\n" +
                "                    \"name\": \"Christopher Wilhelm\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2109360564\",\n" +
                "                    \"name\": \"Caroline Wu\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"82148460\",\n" +
                "                    \"name\": \"Jiangjiang Yang\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"98442688\",\n" +
                "                    \"name\": \"A. Zamarron\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"15292561\",\n" +
                "                    \"name\": \"Madeleine van Zuylen\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1780531\",\n" +
                "                    \"name\": \"Daniel S. Weld\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"7334e12961f8a15e2276217bbdb19709c427b337\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/7334e12961f8a15e2276217bbdb19709c427b337\",\n" +
                "            \"title\": \"Extraction of knowledge graph of Covid-19 through mining of unstructured biomedical corpora\",\n" +
                "            \"abstract\": null,\n" +
                "            \"venue\": \"Computational biology and chemistry\",\n" +
                "            \"year\": 2023,\n" +
                "            \"referenceCount\": 51,\n" +
                "            \"citationCount\": 0,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"2007598141\",\n" +
                "                    \"name\": \"Sudhakaran Gajendran\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"152429494\",\n" +
                "                    \"name\": \"D. Manjula\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1802251\",\n" +
                "                    \"name\": \"V. Sugumaran\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"145936545\",\n" +
                "                    \"name\": \"R. Hema\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"af06ff742c72973c15beed930aeaade2d717e1fa\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/af06ff742c72973c15beed930aeaade2d717e1fa\",\n" +
                "            \"title\": \"LamAPI: a comprehensive tool for string-based entity retrieval with type-base filters\",\n" +
                "            \"abstract\": \"When information available in unstructured or semi-structured formats, e . g ., tables or texts, comes in, finding links between strings appearing in these sources and the entities they refer to in some background Knowledge Graphs (KGs) is a key step to integrate, enrich and extend the data and/or KGs. This Entity Linking task is usually decomposed into Entity Retrieval and Entity Disambiguation because of the large entity search space. This paper presents an Entity Retrieval service (LamAPI) and discusses the impact of different retrieval configurations, i . e ., query and filtering strategies, on the retrieval of entities. The approach is to augment the search activity with extra information, like types, associated with the strings in the original datasets. The results have been empirically validated against public datasets.\",\n" +
                "            \"venue\": \"OM@ISWC\",\n" +
                "            \"year\": 2022,\n" +
                "            \"referenceCount\": 23,\n" +
                "            \"citationCount\": 0,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"1565656201\",\n" +
                "                    \"name\": \"R. Avogadro\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"115251752\",\n" +
                "                    \"name\": \"M. Cremaschi\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2202223394\",\n" +
                "                    \"name\": \"Fabio D'Adda\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1744309\",\n" +
                "                    \"name\": \"F. Paoli\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2525935\",\n" +
                "                    \"name\": \"M. Palmonari\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"d105cda3a8bfe7a86f28e39dfc7884e50708de50\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/d105cda3a8bfe7a86f28e39dfc7884e50708de50\",\n" +
                "            \"title\": \"An eye on representation learning in ontology matching\",\n" +
                "            \"abstract\": \"Representation learning has received increased attention in the last few years in several tasks, including knowledge graph completion, entity resolution, and ontology matching. This paper presents an overview of representation learning approaches applied to the ontology matching task. It proposes to classify such approaches into the following dimensions: lexical unit segmentation, training strategy, and information representation complexity. A discussion on them is presented together with their pros and cons. Perspectives for further developments are also discussed.\",\n" +
                "            \"venue\": \"OM@ISWC\",\n" +
                "            \"year\": 2022,\n" +
                "            \"referenceCount\": 58,\n" +
                "            \"citationCount\": 0,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"2202263084\",\n" +
                "                    \"name\": \"Guilherme Sousa\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"144130746\",\n" +
                "                    \"name\": \"Rinaldo Lima\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"3469495\",\n" +
                "                    \"name\": \"C. Trojahn\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"a0393f5f2c5d38f326e05e70810941ce640a562d\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/a0393f5f2c5d38f326e05e70810941ce640a562d\",\n" +
                "            \"title\": \"Pylon: Table Union Search through Contrastive Representation Learning\",\n" +
                "            \"abstract\": \"The large size and fast growth of data repositories, such as data lakes, has spurred the need for data discovery to help analysts find related data. The problem has become challenging as (i) a user typically does not know what datasets exist in an enormous data repository; and (ii) there is usually a lack of a unified data model to capture the interrelationships between heterogeneous datasets from disparate sources. The common practice in production is to provide a keyword search interface over the metadata of datasets but users often have discovery needs that cannot be precisely expressed by keywords. In this work, we address one important class of discovery needs: finding union-able tables. The task is to find tables in the repository (or on the web) that can be unioned with a given query table. The challenge is to rec-ognize union-able columns that may be represented differently. In this paper, we propose a data-driven learning approach: specifically, an unsupervised representation learning and embedding retrieval task. Our key idea is to exploit self-supervised contrastive learning to learn an embedding model that produces close embeddings for columns with semantically similar values while pushing apart columns with semantically dissimilar values. We then find union-able tables based on similarities between their constituent columns in embedding space. On a real-world dataset, we demonstrate that our best-performing model achieves significant improvements in precision (16% ↑ ), recall (17% ↑ ), and query response time (7x faster) compared to the state-of-the-art.\",\n" +
                "            \"venue\": \"\",\n" +
                "            \"year\": 2023,\n" +
                "            \"referenceCount\": 39,\n" +
                "            \"citationCount\": 0,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"1432234356\",\n" +
                "                    \"name\": \"Tianji Cong\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"145531067\",\n" +
                "                    \"name\": \"H. Jagadish\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"28fdb929d1c4f87bbb9cc0b5bb880567e3c50429\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/28fdb929d1c4f87bbb9cc0b5bb880567e3c50429\",\n" +
                "            \"title\": \"Information extraction pipelines for knowledge graphs\",\n" +
                "            \"abstract\": null,\n" +
                "            \"venue\": \"Knowledge and Information Systems\",\n" +
                "            \"year\": 2023,\n" +
                "            \"referenceCount\": 63,\n" +
                "            \"citationCount\": 0,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"31187184\",\n" +
                "                    \"name\": \"M. Y. Jaradeh\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"145448000\",\n" +
                "                    \"name\": \"Kuldeep Singh\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"153237136\",\n" +
                "                    \"name\": \"M. Stocker\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1697447\",\n" +
                "                    \"name\": \"A. Both\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"145044578\",\n" +
                "                    \"name\": \"S. Auer\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"f9f49d9e8ff142c4dcbff82dfe27448f45408dea\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/f9f49d9e8ff142c4dcbff82dfe27448f45408dea\",\n" +
                "            \"title\": \"A Survey On Few-shot Knowledge Graph Completion with Structural and Commonsense Knowledge\",\n" +
                "            \"abstract\": \"Knowledge graphs (KG) have served as the key component of various natural language processing applications. Commonsense knowledge graphs (CKG) are a special type of KG, where entities and relations are composed of free-form text. However, previous works in KG completion and CKG completion suffer from long-tail relations and newly-added relations which do not have many know triples for training. In light of this, few-shot KG completion (FKGC), which requires the strengths of graph representation learning and few-shot learning, has been proposed to challenge the problem of limited annotated data. In this paper, we comprehensively survey previous attempts on such tasks in the form of a series of methods and applications. Specifically, we first introduce FKGC challenges, commonly used KGs, and CKGs. Then we systematically categorize and summarize existing works in terms of the type of KGs and the methods. Finally, we present applications of FKGC models on prediction tasks in different areas and share our thoughts on future research directions of FKGC.\",\n" +
                "            \"venue\": \"ArXiv\",\n" +
                "            \"year\": 2023,\n" +
                "            \"referenceCount\": 98,\n" +
                "            \"citationCount\": 0,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"2110816708\",\n" +
                "                    \"name\": \"Haodi Ma\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2111220343\",\n" +
                "                    \"name\": \"D. Wang\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"fc25e3412cec8cae9a6ebfa5094f0d056bf72cfd\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/fc25e3412cec8cae9a6ebfa5094f0d056bf72cfd\",\n" +
                "            \"title\": \"Autonomous schema markups based on intelligent computing for search engine optimization\",\n" +
                "            \"abstract\": \"With advances in artificial intelligence and semantic technology, search engines are integrating semantics to address complex search queries to improve the results. This requires identification of well-known concepts or entities and their relationship from web page contents. But the increase in complex unstructured data on web pages has made the task of concept identification overly complex. Existing research focuses on entity recognition from the perspective of linguistic structures such as complete sentences and paragraphs, whereas a huge part of the data on web pages exists as unstructured text fragments enclosed in HTML tags. Ontologies provide schemas to structure the data on the web. However, including them in the web pages requires additional resources and expertise from organizations or webmasters and thus becoming a major hindrance in their large-scale adoption. We propose an approach for autonomous identification of entities from short text present in web pages to populate semantic models based on a specific ontology model. The proposed approach has been applied to a public dataset containing academic web pages. We employ a long short-term memory (LSTM) deep learning network and the random forest machine learning algorithm to predict entities. The proposed methodology gives an overall accuracy of 0.94 on the test dataset, indicating a potential for automated prediction even in the case of a limited number of training samples for various entities, thus, significantly reducing the required manual workload in practical applications.\",\n" +
                "            \"venue\": \"PeerJ Computer Science\",\n" +
                "            \"year\": 2022,\n" +
                "            \"referenceCount\": 63,\n" +
                "            \"citationCount\": 0,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"1412671847\",\n" +
                "                    \"name\": \"Burhan Ud Din Abbasi\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2064713790\",\n" +
                "                    \"name\": \"Iram Fatima\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"47559824\",\n" +
                "                    \"name\": \"H. Mukhtar\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"40161908\",\n" +
                "                    \"name\": \"Sharifullah Khan\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2165725254\",\n" +
                "                    \"name\": \"Abdulaziz Alhumam\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2088138377\",\n" +
                "                    \"name\": \"H. F. Ahmad\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"1122fab167a885d1d2f2a3be2826d2649baf8575\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/1122fab167a885d1d2f2a3be2826d2649baf8575\",\n" +
                "            \"title\": \"BigText-QA: Question Answering over a Large-Scale Hybrid Knowledge Graph\",\n" +
                "            \"abstract\": \"Answering complex questions over textual resources remains a challenging problem—especially when interpreting the fine-grained relationships among multiple entities that occur within a naturallanguage question or clue. Curated knowledge bases (KBs), such as YAGO, DBpedia, Freebase and Wikidata, have been widely used in this context and gained great acceptance for question-answering (QA) applications in the past decade. While current KBs offer a concise representation of structured knowledge, they lack the variety of formulations and semantic nuances as well as the context of information provided by the natural-language sources. With BigText-QA, we aim to develop an integrated QA system which is able to answer questions based on a more redundant form of a knowledge graph (KG) that organizes both structured and unstructured (i.e., “hybrid”) knowledge in a unified graphical representation. BigText-QA thereby is able to combine the best of both worlds—a canonical set of named entities, mapped to a structured background KB (such as YAGO or Wikidata), as well as an open set of textual clauses providing highly diversified relational paraphrases with rich context information.\",\n" +
                "            \"venue\": \"ArXiv\",\n" +
                "            \"year\": 2022,\n" +
                "            \"referenceCount\": 52,\n" +
                "            \"citationCount\": 0,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"2143806543\",\n" +
                "                    \"name\": \"Jin Xu\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"144233860\",\n" +
                "                    \"name\": \"M. Biryukov\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"144530424\",\n" +
                "                    \"name\": \"M. Theobald\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"148325532\",\n" +
                "                    \"name\": \"V. E. Venugopal\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"fff5b14850f6d1a4742bdabbff1ec5f7535d8c04\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/fff5b14850f6d1a4742bdabbff1ec5f7535d8c04\",\n" +
                "            \"title\": \"Knowledge of the Ancestors: Intelligent Ontology-aware Annotation of Biological Literature using Semantic Similarity\",\n" +
                "            \"abstract\": \"Natural language processing models have emerged as a solution to manual curation for fast and automated annotation of literature with ontology concepts. Deep learning architectures have particularly been employed for this task due to increased accuracy over traditional machine learning techniques. One of the greatest limitations in prior work is that the architectures do not use the ontology hierarchy while training or making predictions. These models treat ontology concepts as if they were independent entities while ignoring the semantics and relationships represented in the ontology. Here, we present deep learning architectures for ontology-aware models that use the ontology hierarchy for training and predicting ontology concepts for pieces of text.\",\n" +
                "            \"venue\": \"\",\n" +
                "            \"year\": 2022,\n" +
                "            \"referenceCount\": 21,\n" +
                "            \"citationCount\": 0,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"2193671817\",\n" +
                "                    \"name\": \"D. S. D. Mohanty\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2193671689\",\n" +
                "                    \"name\": \"DR. Prashanti Manda\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        JSONObject expectedJson = new JSONObject(expectedResponse);


        // test
        String response = new SemanticScholarCaller().call(url, method, positives, negatives);

        System.out.println(response);

        assertEquals(new JSONObject(response), expectedJson);
    }

    @Test
    void callGetRequest() throws IOException {

        // set up Search request
        String urlSearch = "http://api.semanticscholar.org/graph/v1/paper/search?query=literature+graph&fields=authors,venue,year,citationCount,referenceCount,abstract,url";
        HttpMethod method = HttpMethod.GET;

        // expected response
        String expectedSearchResponse = "{\n" +
                "    \"total\": 4913858,\n" +
                "    \"offset\": 0,\n" +
                "    \"next\": 10,\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"paperId\": \"649def34f8be52c8b66281af98ae884c09aef38b\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/649def34f8be52c8b66281af98ae884c09aef38b\",\n" +
                "            \"abstract\": \"We describe a deployed scalable system for organizing published scientific literature into a heterogeneous graph to facilitate algorithmic manipulation and discovery. The resulting literature graph consists of more than 280M nodes, representing papers, authors, entities and various interactions between them (e.g., authorships, citations, entity mentions). We reduce literature graph construction into familiar NLP tasks (e.g., entity extraction and linking), point out research challenges due to differences from standard formulations of these tasks, and report empirical results for each task. The methods described in this paper are used to enable semantic features in www.semanticscholar.org.\",\n" +
                "            \"venue\": \"North American Chapter of the Association for Computational Linguistics\",\n" +
                "            \"year\": 2018,\n" +
                "            \"referenceCount\": 27,\n" +
                "            \"citationCount\": 287,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"145585097\",\n" +
                "                    \"name\": \"Waleed Ammar\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"3458736\",\n" +
                "                    \"name\": \"Dirk Groeneveld\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1857797\",\n" +
                "                    \"name\": \"Chandra Bhagavatula\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"46181066\",\n" +
                "                    \"name\": \"Iz Beltagy\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"46230609\",\n" +
                "                    \"name\": \"Miles Crawford\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"145612610\",\n" +
                "                    \"name\": \"Doug Downey\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"38092776\",\n" +
                "                    \"name\": \"Jason Dunkelberger\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"143718836\",\n" +
                "                    \"name\": \"Ahmed Elgohary\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"46411828\",\n" +
                "                    \"name\": \"Sergey Feldman\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"4480314\",\n" +
                "                    \"name\": \"Vu A. Ha\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"143967880\",\n" +
                "                    \"name\": \"Rodney Michael Kinney\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"41018147\",\n" +
                "                    \"name\": \"Sebastian Kohlmeier\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"46258841\",\n" +
                "                    \"name\": \"Kyle Lo\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"144240185\",\n" +
                "                    \"name\": \"Tyler C. Murray\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"46256862\",\n" +
                "                    \"name\": \"Hsu-Han Ooi\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"39139825\",\n" +
                "                    \"name\": \"Matthew E. Peters\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"39561369\",\n" +
                "                    \"name\": \"Joanna L. Power\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"46181683\",\n" +
                "                    \"name\": \"Sam Skjonsberg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"31860505\",\n" +
                "                    \"name\": \"Lucy Lu Wang\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"46212260\",\n" +
                "                    \"name\": \"Christopher Wilhelm\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2112339497\",\n" +
                "                    \"name\": \"Zheng Yuan\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"15292561\",\n" +
                "                    \"name\": \"Madeleine van Zuylen\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1741101\",\n" +
                "                    \"name\": \"Oren Etzioni\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"8232620ca65cd29b3c181a2dbf0daf918650bda5\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/8232620ca65cd29b3c181a2dbf0daf918650bda5\",\n" +
                "            \"abstract\": null,\n" +
                "            \"venue\": \"\",\n" +
                "            \"year\": 2020,\n" +
                "            \"referenceCount\": 0,\n" +
                "            \"citationCount\": 2,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"26416892\",\n" +
                "                    \"name\": \"D. Floros\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2548579\",\n" +
                "                    \"name\": \"N. Pitsianis\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"3155819\",\n" +
                "                    \"name\": \"Xiaobai Sun\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"4c6dfe48b8b136e6bb44c92e1fa006d3d7bf71ed\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/4c6dfe48b8b136e6bb44c92e1fa006d3d7bf71ed\",\n" +
                "            \"abstract\": \"We introduce GrapAL (Graph database of Academic Literature), a versatile tool for exploring and investigating scientific literature which satisfies a variety of use cases and information needs requested by researchers. At the core of GrapAL is a Neo4j graph database with an intuitive schema and a simple query language. In this paper, we describe the basic elements of GrapAL, how to use it, and several use cases such as finding experts on a given topic for peer reviewing, discovering indirect connections between biomedical entities, and computing citation-based metrics.\",\n" +
                "            \"venue\": \"ArXiv\",\n" +
                "            \"year\": 2019,\n" +
                "            \"referenceCount\": 12,\n" +
                "            \"citationCount\": 5,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"144199866\",\n" +
                "                    \"name\": \"Christine Betts\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"39561369\",\n" +
                "                    \"name\": \"Joanna L. Power\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"145585097\",\n" +
                "                    \"name\": \"Waleed Ammar\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"fffbf0cbafa24f17fe5db8582efaa3406ebd08a4\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/fffbf0cbafa24f17fe5db8582efaa3406ebd08a4\",\n" +
                "            \"abstract\": null,\n" +
                "            \"venue\": \"Decision Support Systems\",\n" +
                "            \"year\": 2020,\n" +
                "            \"referenceCount\": 91,\n" +
                "            \"citationCount\": 100,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"41199155\",\n" +
                "                    \"name\": \"T. Pourhabibi\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"145878480\",\n" +
                "                    \"name\": \"K. Ong\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1685480\",\n" +
                "                    \"name\": \"B. Kam\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2244944\",\n" +
                "                    \"name\": \"Yee Ling Boo\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"328d027a999efc9d9e315367f5e01096ef4e7255\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/328d027a999efc9d9e315367f5e01096ef4e7255\",\n" +
                "            \"abstract\": \"To combat COVID-19, both clinicians and scientists need to digest the vast amount of relevant biomedical knowledge in literature to understand the disease mechanism and the related biological functions. We have developed a novel and comprehensive knowledge discovery framework, COVID-KG to extract fine-grained multimedia knowledge elements (entities, relations and events) from scientific literature. We then exploit the constructed multimedia knowledge graphs (KGs) for question answering and report generation, using drug repurposing as a case study. Our framework also provides detailed contextual sentences, subfigures, and knowledge subgraphs as evidence. All of the data, KGs, reports.\",\n" +
                "            \"venue\": \"North American Chapter of the Association for Computational Linguistics\",\n" +
                "            \"year\": 2020,\n" +
                "            \"referenceCount\": 73,\n" +
                "            \"citationCount\": 67,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"1786863\",\n" +
                "                    \"name\": \"Qingyun Wang\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"3361240\",\n" +
                "                    \"name\": \"Manling Li\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2154990549\",\n" +
                "                    \"name\": \"Xuan Wang\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"147697560\",\n" +
                "                    \"name\": \"Nikolaus Nova Parulian\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2067641876\",\n" +
                "                    \"name\": \"G. Han\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"152320135\",\n" +
                "                    \"name\": \"Jiawei Ma\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"71125575\",\n" +
                "                    \"name\": \"Jingxuan Tu\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"46179573\",\n" +
                "                    \"name\": \"Ying Lin\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"46702624\",\n" +
                "                    \"name\": \"H. Zhang\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2109300810\",\n" +
                "                    \"name\": \"Weili Liu\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"72446317\",\n" +
                "                    \"name\": \"Aabhas Chauhan\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2069571239\",\n" +
                "                    \"name\": \"Yingjun Guan\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1596827240\",\n" +
                "                    \"name\": \"Bangzheng Li\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"5858064\",\n" +
                "                    \"name\": \"Ruisong Li\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"19214393\",\n" +
                "                    \"name\": \"Xiangchen Song\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2113323573\",\n" +
                "                    \"name\": \"Heng Ji\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"153034701\",\n" +
                "                    \"name\": \"Jiawei Han\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"9546964\",\n" +
                "                    \"name\": \"Shih-Fu Chang\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1707726\",\n" +
                "                    \"name\": \"J. Pustejovsky\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"72861332\",\n" +
                "                    \"name\": \"D. Liem\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"144332315\",\n" +
                "                    \"name\": \"Ahmed Elsayed\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"145755155\",\n" +
                "                    \"name\": \"Martha Palmer\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1703851645\",\n" +
                "                    \"name\": \"Jasmine Rah\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2064735303\",\n" +
                "                    \"name\": \"Cynthia Schneider\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2008000\",\n" +
                "                    \"name\": \"B. Onyshkevych\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"e39a0dfe48da9a51defb80fb4b839418e2981b8d\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/e39a0dfe48da9a51defb80fb4b839418e2981b8d\",\n" +
                "            \"abstract\": \"The coronavirus disease (COVID-19) has claimed the lives of over one million people and infected more than thirty-five million people worldwide. Several search engines have surfaced to provide researchers with additional tools to find and retrieve information from the rapidly growing corpora on COVID19. These engines lack extraction and visualization tools necessary to retrieve and interpret complex relations inherent to scientific literature. Moreover, because these engines mainly rely upon semantic information, their ability to capture complex global relationships across documents is limited, which reduces the quality of similarity-based article recommendations for users. In this work, we present the COVID-19 Knowledge Graph (CKG), a heterogeneous graph for extracting and visualizing complex relationships between COVID-19 scientific articles. The CKG combines semantic information with document topological information for the application of similar document retrieval. The CKG is constructed using the latent schema of the data, and then enriched with biomedical entity information extracted from the unstructured text of articles using scalable AWS technologies to form relations in the graph. Finally, we propose a document similarity engine that leverages low-dimensional graph embeddings from the CKG with semantic embeddings for similar article retrieval. Analysis demonstrates the quality of relationships in the CKG and shows that it can be used to uncover meaningful information in COVID-19 scientific articles. The CKG helps power www.cord19.aws and is publicly available.\",\n" +
                "            \"venue\": \"KNLP\",\n" +
                "            \"year\": 2020,\n" +
                "            \"referenceCount\": 28,\n" +
                "            \"citationCount\": 45,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"1824126957\",\n" +
                "                    \"name\": \"Colby Wise\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"40043851\",\n" +
                "                    \"name\": \"V. N. Ioannidis\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2390173\",\n" +
                "                    \"name\": \"Miguel Calvo Rebollar\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2118943843\",\n" +
                "                    \"name\": \"Xiang Song\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1486136128\",\n" +
                "                    \"name\": \"George D. Price\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2052302708\",\n" +
                "                    \"name\": \"Ninad Kulkarni\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2066945288\",\n" +
                "                    \"name\": \"Ryan Brand\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"50339091\",\n" +
                "                    \"name\": \"Parminder Bhatia\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"50877490\",\n" +
                "                    \"name\": \"G. Karypis\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"2403fea7cff0aa92565edf45f17f1d4fdbe107cc\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/2403fea7cff0aa92565edf45f17f1d4fdbe107cc\",\n" +
                "            \"abstract\": \"As scientists worldwide search for answers to the overwhelmingly unknown behind the deadly pandemic, the literature concerning COVID-19 has been growing exponentially. Keeping abreast of the body of literature at such a rapidly advancing pace poses significant challenges not only to active researchers but also to society as a whole. Although numerous data resources have been made openly available, the analytic and synthetic process that is essential in effectively navigating through the vast amount of information with heightened levels of uncertainty remains a significant bottleneck. We introduce a generic method that facilitates the data collection and sense-making process when dealing with a rapidly growing landscape of a research domain such as COVID-19 at multiple levels of granularity. The method integrates the analysis of structural and temporal patterns in scholarly publications with the delineation of thematic concentrations and the types of uncertainties that may offer additional insights into the complexity of the unknown. We demonstrate the application of the method in a study of the COVID-19 literature.\",\n" +
                "            \"venue\": \"Frontiers in Research Metrics and Analytics\",\n" +
                "            \"year\": 2020,\n" +
                "            \"referenceCount\": 100,\n" +
                "            \"citationCount\": 49,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"11699416\",\n" +
                "                    \"name\": \"Chaomei Chen\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"23ae48cdb8b7985e5a32fc79b6aae0de3230fe4f\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/23ae48cdb8b7985e5a32fc79b6aae0de3230fe4f\",\n" +
                "            \"abstract\": \"One in ten people are affected by rare diseases, and three out of ten children with rare diseases will not live past age five. However, the small market size of individual rare diseases, combined with the time and capital requirements of pharmaceutical R&D, have hindered the development of new drugs for these cases. A promising alternative is drug repurposing, whereby existing FDA-approved drugs might be used to treat diseases different from their original indications. In order to generate drug repurposing hypotheses in a systematic and comprehensive fashion, it is essential to integrate information from across the literature of pharmacology, genetics, and pathology. To this end, we leverage a newly developed knowledge graph, the Global Network of Biomedical Relationships (GNBR). GNBR is a large, heterogeneous knowledge graph comprising drug, disease, and gene (or protein) entities linked by a small set of semantic themes derived from the abstracts of biomedical literature. We apply a knowledge graph embedding method that explicitly models the uncertainty associated with literature-derived relationships and uses link prediction to generate drug repurposing hypotheses. This approach achieves high performance on a gold-standard test set of known drug indications (AUROC = 0.89) and is capable of generating novel repurposing hypotheses, which we independently validate using external literature sources and protein interaction networks. Finally, we demonstrate the ability of our model to produce explanations of its predictions.\",\n" +
                "            \"venue\": \"bioRxiv\",\n" +
                "            \"year\": 2019,\n" +
                "            \"referenceCount\": 38,\n" +
                "            \"citationCount\": 50,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"89791075\",\n" +
                "                    \"name\": \"Daniel N. Sosa\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"50341000\",\n" +
                "                    \"name\": \"Alexander Derry\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"3358559\",\n" +
                "                    \"name\": \"Margaret Guo\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1758766161\",\n" +
                "                    \"name\": \"Eric Wei\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"51968756\",\n" +
                "                    \"name\": \"Connor Brinton\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"144446128\",\n" +
                "                    \"name\": \"R. Altman\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"c14268fc7ecf930ccea26419d1da25d324686580\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/c14268fc7ecf930ccea26419d1da25d324686580\",\n" +
                "            \"abstract\": null,\n" +
                "            \"venue\": \"Computational Geosciences\",\n" +
                "            \"year\": 2018,\n" +
                "            \"referenceCount\": 52,\n" +
                "            \"citationCount\": 103,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"1390842583\",\n" +
                "                    \"name\": \"Chengbin Wang\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"1920715\",\n" +
                "                    \"name\": \"Xiaogang Ma\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"49252412\",\n" +
                "                    \"name\": \"Jianguo Chen\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"49252209\",\n" +
                "                    \"name\": \"Jingwen Chen\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"paperId\": \"298501f17584d4cdd3f73527c19ca547c9610e96\",\n" +
                "            \"url\": \"https://www.semanticscholar.org/paper/298501f17584d4cdd3f73527c19ca547c9610e96\",\n" +
                "            \"abstract\": null,\n" +
                "            \"venue\": \"BMC Bioinformatics\",\n" +
                "            \"year\": 2018,\n" +
                "            \"referenceCount\": 31,\n" +
                "            \"citationCount\": 57,\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"authorId\": \"3165635\",\n" +
                "                    \"name\": \"Shengtian Sang\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"151472911\",\n" +
                "                    \"name\": \"Zhihao Yang\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"145131943\",\n" +
                "                    \"name\": \"Lei Wang\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"2110754495\",\n" +
                "                    \"name\": \"Xiaoxia Liu\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"37553559\",\n" +
                "                    \"name\": \"Hongfei Lin\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"authorId\": \"49605993\",\n" +
                "                    \"name\": \"Jian Wang\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        // test
        String searchResponse = new SemanticScholarCaller().call(urlSearch, method, null, null);

        assertEquals(searchResponse, expectedSearchResponse);

    }

}