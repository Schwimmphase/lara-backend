package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.util.List;

class SemanticScholarWrapperTest {

    String jsonPaper = "{\n" +
            "    \"paperId\": \"649def34f8be52c8b66281af98ae884c09aef38b\",\n" +
            "    \"url\": \"https://www.semanticscholar.org/paper/649def34f8be52c8b66281af98ae884c09aef38b\",\n" +
            "    \"title\": \"Construction of the Literature Graph in Semantic Scholar\",\n" +
            "    \"abstract\": \"We describe a deployed scalable system for organizing published scientific literature into a heterogeneous graph to facilitate algorithmic manipulation and discovery. The resulting literature graph consists of more than 280M nodes, representing papers, authors, entities and various interactions between them (e.g., authorships, citations, entity mentions). We reduce literature graph construction into familiar NLP tasks (e.g., entity extraction and linking), point out research challenges due to differences from standard formulations of these tasks, and report empirical results for each task. The methods described in this paper are used to enable semantic features in www.semanticscholar.org.\",\n" +
            "    \"venue\": \"North American Chapter of the Association for Computational Linguistics\",\n" +
            "    \"year\": 2018,\n" +
            "    \"referenceCount\": 27,\n" +
            "    \"citationCount\": 286,\n" +
            "    \"authors\": [\n" +
            "        {\n" +
            "            \"authorId\": \"145585097\",\n" +
            "            \"name\": \"Waleed Ammar\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"3458736\",\n" +
            "            \"name\": \"Dirk Groeneveld\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"1857797\",\n" +
            "            \"name\": \"Chandra Bhagavatula\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"46181066\",\n" +
            "            \"name\": \"Iz Beltagy\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"46230609\",\n" +
            "            \"name\": \"Miles Crawford\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"145612610\",\n" +
            "            \"name\": \"Doug Downey\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"38092776\",\n" +
            "            \"name\": \"Jason Dunkelberger\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"143718836\",\n" +
            "            \"name\": \"Ahmed Elgohary\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"46411828\",\n" +
            "            \"name\": \"Sergey Feldman\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"4480314\",\n" +
            "            \"name\": \"Vu A. Ha\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"143967880\",\n" +
            "            \"name\": \"Rodney Michael Kinney\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"41018147\",\n" +
            "            \"name\": \"Sebastian Kohlmeier\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"46258841\",\n" +
            "            \"name\": \"Kyle Lo\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"144240185\",\n" +
            "            \"name\": \"Tyler C. Murray\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"46256862\",\n" +
            "            \"name\": \"Hsu-Han Ooi\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"39139825\",\n" +
            "            \"name\": \"Matthew E. Peters\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"39561369\",\n" +
            "            \"name\": \"Joanna L. Power\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"46181683\",\n" +
            "            \"name\": \"Sam Skjonsberg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"31860505\",\n" +
            "            \"name\": \"Lucy Lu Wang\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"46212260\",\n" +
            "            \"name\": \"Christopher Wilhelm\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"2112339497\",\n" +
            "            \"name\": \"Zheng Yuan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"15292561\",\n" +
            "            \"name\": \"Madeleine van Zuylen\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"1741101\",\n" +
            "            \"name\": \"Oren Etzioni\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    String jsonReferences = "{\n" +
            "    \"offset\": 0,\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"3493c33b36f005d230469c456256cb1fe144376f\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/3493c33b36f005d230469c456256cb1fe144376f\",\n" +
            "                \"title\": \"An NLP Curator (or: How I Learned to Stop Worrying and Love NLP Pipelines)\",\n" +
            "                \"abstract\": \"Natural Language Processing continues to grow in popularity in a range of research and commercial applications, yet managing the wide array of potential NLP components remains a difficult problem. This paper describes Curator, an NLP management framework designed to address some common problems and inefficiencies associated with building NLP process pipelines; and Edison, an NLP data structure library in Java that provides streamlined interactions with Curator and offers a range of useful supporting functionality.\",\n" +
            "                \"venue\": \"International Conference on Language Resources and Evaluation\",\n" +
            "                \"year\": 2012,\n" +
            "                \"referenceCount\": 13,\n" +
            "                \"citationCount\": 55,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"50649676\",\n" +
            "                        \"name\": \"J. Clarke\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3052879\",\n" +
            "                        \"name\": \"Vivek Srikumar\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3149169\",\n" +
            "                        \"name\": \"M. Sammons\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"144590225\",\n" +
            "                        \"name\": \"D. Roth\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"609ed85b5ae171131c6d89e68aaf93505b73dac4\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/609ed85b5ae171131c6d89e68aaf93505b73dac4\",\n" +
            "                \"title\": \"Learning English Light Verb Constructions: Contextual or Statistical\",\n" +
            "                \"abstract\": \"In this paper, we investigate a supervised machine learning framework for automatically learning of English Light Verb Constructions (LVCs). Our system achieves an 86.3% accuracy with a baseline (chance) performance of 52.2% when trained with groups of either contextual or statistical features. In addition, we present an in-depth analysis of these contextual and statistical features and show that the system trained by these two types of cosmetically different features reaches similar performance empirically. However, in the situation where the surface structures of candidate LVCs are identical, the system trained with contextual features which contain information on surrounding words performs 16.7% better. \\n \\nIn this study, we also construct a balanced benchmark dataset with 2,162 sentences from BNC for English LVCs. And this data set is publicly available and is also a useful computational resource for research on MWEs in general.\",\n" +
            "                \"venue\": \"MWE@ACL\",\n" +
            "                \"year\": 2011,\n" +
            "                \"referenceCount\": 35,\n" +
            "                \"citationCount\": 59,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"2836175\",\n" +
            "                        \"name\": \"Yuancheng Tu\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"144590225\",\n" +
            "                        \"name\": \"D. Roth\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"273dfbcb68080251f5e9ff38b4413d7bd84b10a1\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/273dfbcb68080251f5e9ff38b4413d7bd84b10a1\",\n" +
            "                \"title\": \"LIBSVM: A library for support vector machines\",\n" +
            "                \"abstract\": \"LIBSVM is a library for Support Vector Machines (SVMs). We have been actively developing this package since the year 2000. The goal is to help users to easily apply SVM to their applications. LIBSVM has gained wide popularity in machine learning and many other areas. In this article, we present all implementation details of LIBSVM. Issues such as solving SVM optimization problems theoretical convergence multiclass classification probability estimates and parameter selection are discussed in detail.\",\n" +
            "                \"venue\": \"TIST\",\n" +
            "                \"year\": 2011,\n" +
            "                \"referenceCount\": 59,\n" +
            "                \"citationCount\": 41871,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": null,\n" +
            "                        \"name\": \"Chih-Chung Chang\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1711460\",\n" +
            "                        \"name\": \"Chih-Jen Lin\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"b18b799d4d7f4ee2ec0f72077fdc7ef449854f6b\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/b18b799d4d7f4ee2ec0f72077fdc7ef449854f6b\",\n" +
            "                \"title\": \"How to pick out token instances of English verb-particle constructions\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"Language Resources and Evaluation\",\n" +
            "                \"year\": 2010,\n" +
            "                \"referenceCount\": 47,\n" +
            "                \"citationCount\": 30,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"1736741380\",\n" +
            "                        \"name\": \"Su Nam Kim\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"145465286\",\n" +
            "                        \"name\": \"Timothy Baldwin\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"6608189cc1941748787f9f7948ec9bacb2d2615b\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/6608189cc1941748787f9f7948ec9bacb2d2615b\",\n" +
            "                \"title\": \"Classifying Particle Semantics in English Verb-Particle Constructions\",\n" +
            "                \"abstract\": \"Previous computational work on learning the semantic properties of verb-particle constructions (VPCs) has focused on their compositionality, and has left unaddressed the issue of which meaning of the component words is being used in a given VPC. We develop a feature space for use in classification of the sense contributed by the particle in a VPC, and test this on VPCs using the particle up. The features that capture linguistic properties of VPCs that are relevant to the semantics of the particle outperform linguistically uninformed word co-occurrence features in our experiments on unseen test VPCs.\",\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2006,\n" +
            "                \"referenceCount\": 33,\n" +
            "                \"citationCount\": 46,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"145130572\",\n" +
            "                        \"name\": \"Paul Cook\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"145584212\",\n" +
            "                        \"name\": \"S. Stevenson\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": null,\n" +
            "                \"url\": null,\n" +
            "                \"title\": \"Computational Linguistics Dimensions of the Syntax and Semantics of Prepositions, chapter Verb-Particel Constructions in the World Wide Web\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"Computational Linguistics Dimensions of the Syntax and Semantics of Prepositions, chapter Verb-Particel Constructions in the World Wide Web\",\n" +
            "                \"year\": 2006,\n" +
            "                \"referenceCount\": null,\n" +
            "                \"citationCount\": null,\n" +
            "                \"authors\": []\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": null,\n" +
            "                \"url\": null,\n" +
            "                \"title\": \"Computational Linguistics Di\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2006,\n" +
            "                \"referenceCount\": null,\n" +
            "                \"citationCount\": null,\n" +
            "                \"authors\": []\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"0ecb33ced5b0976accdf13817151f80568b6fdcb\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/0ecb33ced5b0976accdf13817151f80568b6fdcb\",\n" +
            "                \"title\": \"Coarse-to-Fine n-Best Parsing and MaxEnt Discriminative Reranking\",\n" +
            "                \"abstract\": \"Discriminative reranking is one method for constructing high-performance statistical parsers (Collins, 2000). A discriminative reranker requires a source of candidate parses for each sentence. This paper describes a simple yet novel method for constructing sets of 50-best parses based on a coarse-to-fine generative parser (Charniak, 2000). This method generates 50-best lists that are of substantially higher quality than previously obtainable. We used these parses as the input to a MaxEnt reranker (Johnson et al., 1999; Riezler et al., 2002) that selects the best parse from the set of parses for each sentence, obtaining an f-score of 91.0% on sentences of length 100 or less.\",\n" +
            "                \"venue\": \"Annual Meeting of the Association for Computational Linguistics\",\n" +
            "                \"year\": 2005,\n" +
            "                \"referenceCount\": 22,\n" +
            "                \"citationCount\": 1266,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"1749837\",\n" +
            "                        \"name\": \"Eugene Charniak\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"145177220\",\n" +
            "                        \"name\": \"Mark Johnson\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"a2e007387f58174eba86e34a9ea605c850c2f1ee\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/a2e007387f58174eba86e34a9ea605c850c2f1ee\",\n" +
            "                \"title\": \"Verb-Particle Constructions and Lexical Resources\",\n" +
            "                \"abstract\": \"In this paper we investigate the phenomenon of verb-particle constructions, discussing their characteristics and their availability for use with NLP systems. We concentrate in particular on the coverage provided by some electronic resources. Given the constantly growing number of verb-particle combinations, possible ways of extending the coverage of the available resources are investigated, taking into account regular patterns found in some productive combinations of verbs and particles. We discuss, in particular, the use of Levin's (1993) classes of verbs as a means to obtain productive verb-particle constructions, and discuss the issues involved in adopting such an approach.\",\n" +
            "                \"venue\": \"Annual Meeting of the Association for Computational Linguistics\",\n" +
            "                \"year\": 2003,\n" +
            "                \"referenceCount\": 17,\n" +
            "                \"citationCount\": 52,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"145585242\",\n" +
            "                        \"name\": \"Aline Villavicencio\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"3df6c9f6cea7b9217fd2b9388afe8b40029d4fb1\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/3df6c9f6cea7b9217fd2b9388afe8b40029d4fb1\",\n" +
            "                \"title\": \"An Expert Lexicon Approach to Identifying English Phrasal Verbs\",\n" +
            "                \"abstract\": \"Phrasal Verbs are an important feature of the English language. Properly identifying them provides the basis for an English parser to decode the related structures. Phrasal verbs have been a challenge to Natural Language Processing (NLP) because they sit at the borderline between lexicon and syntax. Traditional NLP frameworks that separate the lexicon module from the parser make it difficult to handle this problem properly. This paper presents a finite state approach that integrates a phrasal verb expert lexicon between shallow parsing and deep parsing to handle morpho-syntactic interaction. With precision/recall combined performance benchmarked consistently at 95.8%-97.5%, the Phrasal Verb identification problem has basically been solved with the presented method.\",\n" +
            "                \"venue\": \"Annual Meeting of the Association for Computational Linguistics\",\n" +
            "                \"year\": 2003,\n" +
            "                \"referenceCount\": 15,\n" +
            "                \"citationCount\": 44,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"50135754\",\n" +
            "                        \"name\": \"W. Li\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2155283599\",\n" +
            "                        \"name\": \"Xiuhong Zhang\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"144632211\",\n" +
            "                        \"name\": \"Cheng Niu\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2117807644\",\n" +
            "                        \"name\": \"Yuankai Jiang\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1748081\",\n" +
            "                        \"name\": \"R. Srihari\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"834b6e59c12a4a1175286654d235c5a32f9caa2e\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/834b6e59c12a4a1175286654d235c5a32f9caa2e\",\n" +
            "                \"title\": \"Verb-particle constructions in a computational grammar of English\",\n" +
            "                \"abstract\": \"In this paper we investigate the phenomenon of verb-particle constructions, discussing their characteristics and the challenges that they present for a computational grammar. We concentrate our discussion on the treatment adopted in a wide-coverage HPSG grammar: the LinGO ERG. Given the constantly growing number of verb-particle combinations, possible ways of extending this treatment are investigated, taking into account the regular patterns found in some productive combinations of verbs and particles. We analyse possible ways of identifying regular patterns using different resources. One possible way to try to capture these is by means of lexical rules, and we discuss the difficulties encountered when adopting such an approach. We also investigate how to restrict the productivity of lexical rules to deal with subregularities and exceptions to the patterns found.\",\n" +
            "                \"venue\": \"Proceedings of the International Conference on Head-Driven Phrase Structure Grammar\",\n" +
            "                \"year\": 2003,\n" +
            "                \"referenceCount\": 32,\n" +
            "                \"citationCount\": 24,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"145585242\",\n" +
            "                        \"name\": \"Aline Villavicencio\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"15379653\",\n" +
            "                        \"name\": \"Ann A. Copestake\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"5504f4ec7b7b7b074e9dc557beeaf68c76b65540\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/5504f4ec7b7b7b074e9dc557beeaf68c76b65540\",\n" +
            "                \"title\": \"Multiword Expressions: A Pain in the Neck for NLP\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"Conference on Intelligent Text Processing and Computational Linguistics\",\n" +
            "                \"year\": 2002,\n" +
            "                \"referenceCount\": 33,\n" +
            "                \"citationCount\": 1211,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"2393013\",\n" +
            "                        \"name\": \"I. Sag\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"145465286\",\n" +
            "                        \"name\": \"Timothy Baldwin\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"145440721\",\n" +
            "                        \"name\": \"Francis Bond\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"15379653\",\n" +
            "                        \"name\": \"Ann A. Copestake\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3209288\",\n" +
            "                        \"name\": \"D. Flickinger\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"7fa22a5cb618ebdceba4421b0628005ebaaea2c0\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/7fa22a5cb618ebdceba4421b0628005ebaaea2c0\",\n" +
            "                \"title\": \"English particle constructions, the lexicon, and the autonomy of syntax\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2002,\n" +
            "                \"referenceCount\": 0,\n" +
            "                \"citationCount\": 154,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"1766112\",\n" +
            "                        \"name\": \"R. Jackendoff\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": null,\n" +
            "                \"url\": null,\n" +
            "                \"title\": \"Proc. of the 3rd International Conference on Intelligent Text Processing and Computational Linguistics\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"Proc. of the 3rd International Conference on Intelligent Text Processing and Computational Linguistics\",\n" +
            "                \"year\": 2002,\n" +
            "                \"referenceCount\": null,\n" +
            "                \"citationCount\": null,\n" +
            "                \"authors\": []\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"3fab92869cfab684b3ffb1c16a771e9c3b774acd\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/3fab92869cfab684b3ffb1c16a771e9c3b774acd\",\n" +
            "                \"title\": \"The Use of Classifiers in Sequential Inference\",\n" +
            "                \"abstract\": \"We study the problem of combining the outcomes of several different classifiers in a way that provides a coherent inference that satisfies some constraints. In particular, we develop two general approaches for an important subproblem - identifying phrase structure. The first is a Markovian approach that extends standard HMMs to allow the use of a rich observation structure and of general classifiers to model state-observation dependencies. The second is an extension of constraint satisfaction formalisms. We develop efficient combination algorithms under both models and study them experimentally in the context of shallow parsing.\",\n" +
            "                \"venue\": \"NIPS\",\n" +
            "                \"year\": 2001,\n" +
            "                \"referenceCount\": 31,\n" +
            "                \"citationCount\": 222,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"2474158\",\n" +
            "                        \"name\": \"Vasin Punyakanok\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"144590225\",\n" +
            "                        \"name\": \"D. Roth\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"d87ceda3042f781c341ac17109d1e94a717f5f60\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/d87ceda3042f781c341ac17109d1e94a717f5f60\",\n" +
            "                \"title\": \"WordNet : an electronic lexical database\",\n" +
            "                \"abstract\": \"Part 1 The lexical database: nouns in WordNet, George A. Miller modifiers in WordNet, Katherine J. Miller a semantic network of English verbs, Christiane Fellbaum design and implementation of the WordNet lexical database and searching software, Randee I. Tengi. Part 2: automated discovery of WordNet relations, Marti A. Hearst representing verb alterations in WordNet, Karen T. Kohl et al the formalization of WordNet by methods of relational concept analysis, Uta E. Priss. Part 3 Applications of WordNet: building semantic concordances, Shari Landes et al performance and confidence in a semantic annotation task, Christiane Fellbaum et al WordNet and class-based probabilities, Philip Resnik combining local context and WordNet similarity for word sense identification, Claudia Leacock and Martin Chodorow using WordNet for text retrieval, Ellen M. Voorhees lexical chains as representations of context for the detection and correction of malapropisms, Graeme Hirst and David St-Onge temporal indexing through lexical chaining, Reem Al-Halimi and Rick Kazman COLOR-X - using knowledge from WordNet for conceptual modelling, J.F.M. Burg and R.P. van de Riet knowledge processing on an extended WordNet, Sanda M. Harabagiu and Dan I Moldovan appendix - obtaining and using WordNet.\",\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2000,\n" +
            "                \"referenceCount\": 7,\n" +
            "                \"citationCount\": 13868,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"1721801\",\n" +
            "                        \"name\": \"C. Fellbaum\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": null,\n" +
            "                \"url\": null,\n" +
            "                \"title\": \"1998.Understanding English Grammar\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 1998,\n" +
            "                \"referenceCount\": null,\n" +
            "                \"citationCount\": null,\n" +
            "                \"authors\": []\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"e393f6f5d0affbdf3d4b2f82de99fa75e16376c5\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/e393f6f5d0affbdf3d4b2f82de99fa75e16376c5\",\n" +
            "                \"title\": \"The phrasal verb in English\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 1974,\n" +
            "                \"referenceCount\": 0,\n" +
            "                \"citationCount\": 357,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"50552632\",\n" +
            "                        \"name\": \"D. Bolinger\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"df3ff6bd539eaa5dca2804c029b6712c8dcb7ae0\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/df3ff6bd539eaa5dca2804c029b6712c8dcb7ae0\",\n" +
            "                \"title\": \"Understanding English grammar\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 1971,\n" +
            "                \"referenceCount\": 0,\n" +
            "                \"citationCount\": 73,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"69522281\",\n" +
            "                        \"name\": \"速川 和男\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": null,\n" +
            "                \"url\": null,\n" +
            "                \"title\": \"1971.The Phrasal Verb in English\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 1971,\n" +
            "                \"referenceCount\": null,\n" +
            "                \"citationCount\": null,\n" +
            "                \"authors\": []\n" +
            "            }\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    String jsonRecommendations = "{\n" +
            "    \"recommendedPapers\": [\n" +
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
            "        },\n" +
            "        {\n" +
            "            \"paperId\": \"ef9fcab68d44e55e1e44439c26ede109e892f591\",\n" +
            "            \"url\": \"https://www.semanticscholar.org/paper/ef9fcab68d44e55e1e44439c26ede109e892f591\",\n" +
            "            \"title\": \"MORTY: Structured Summarization for Targeted Information Extraction from Scholarly Articles\",\n" +
            "            \"abstract\": null,\n" +
            "            \"venue\": \"International Conference on Asian Digital Libraries\",\n" +
            "            \"year\": 2022,\n" +
            "            \"referenceCount\": 43,\n" +
            "            \"citationCount\": 0,\n" +
            "            \"authors\": [\n" +
            "                {\n" +
            "                    \"authorId\": \"31187184\",\n" +
            "                    \"name\": \"M. Y. Jaradeh\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"153237136\",\n" +
            "                    \"name\": \"M. Stocker\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"145044578\",\n" +
            "                    \"name\": \"S. Auer\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"paperId\": \"00971ecf9ea497286c0c454b34ff4ff355170df2\",\n" +
            "            \"url\": \"https://www.semanticscholar.org/paper/00971ecf9ea497286c0c454b34ff4ff355170df2\",\n" +
            "            \"title\": \"Open Relation and Event Type Discovery with Type Abstraction\",\n" +
            "            \"abstract\": \"Conventional “closed-world\\\" information extraction (IE) approaches rely on human ontolo-gies to deﬁne the scope for extraction. As a result, such approaches fall short when applied to new domains. This calls for systems that can automatically infer new types from given corpora, a task which we refer to as type discovery . To tackle this problem, we introduce the idea of type abstraction, where the model is prompted to generalize and name the type. Then we use the similarity between inferred names to induce clusters. Observing that this abstraction-based representation is often complementary to the entity/trigger token representation, we set up these two representations as two views and design our model as a co-training framework. Our experiments on multiple relation extraction and event extraction datasets consistently show the advantage of our type abstraction approach.\",\n" +
            "            \"venue\": \"ArXiv\",\n" +
            "            \"year\": 2022,\n" +
            "            \"referenceCount\": 39,\n" +
            "            \"citationCount\": 0,\n" +
            "            \"authors\": [\n" +
            "                {\n" +
            "                    \"authorId\": \"2109154767\",\n" +
            "                    \"name\": \"Sha Li\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2181650518\",\n" +
            "                    \"name\": \"Heng Ji\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2111759643\",\n" +
            "                    \"name\": \"Jiawei Han\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"paperId\": \"a85f8b366f368bfef168e2090adb33a9bbafcb7d\",\n" +
            "            \"url\": \"https://www.semanticscholar.org/paper/a85f8b366f368bfef168e2090adb33a9bbafcb7d\",\n" +
            "            \"title\": \"UvA-DARE (Digital Academic Repository) Towards Entity Spaces Towards Entity Spaces\",\n" +
            "            \"abstract\": \"Entities are a central element of knowledge bases and are important input to many knowledge-centric tasks including text analysis. For example, they allow us to ﬁnd documents relevant to a speciﬁc entity irrespective of the underlying syntactic expression within a document. However, the entities that are commonly represented in knowledge bases are often a simpliﬁcation of what is truly being referred to in text. For example, in a knowledge base, we may have an entity for Germany as a country but not for the more fuzzy concept of Germany that covers notions of German Population, German Drivers, and the German Government. Inspired by recent advances in contextual word embeddings, we introduce the concept of entity spaces - speciﬁc representations of a set of associated entities with near-identity. Thus, these entity spaces provide a handle to an amorphous grouping of entities. We developed a proof-of-concept for English showing how, through the introduction of entity spaces in the form of disambiguation pages, the recall of entity linking can be improved.\",\n" +
            "            \"venue\": \"\",\n" +
            "            \"year\": 2020,\n" +
            "            \"referenceCount\": 35,\n" +
            "            \"citationCount\": 0,\n" +
            "            \"authors\": [\n" +
            "                {\n" +
            "                    \"authorId\": \"2064426690\",\n" +
            "                    \"name\": \"F. Béchet\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2652453\",\n" +
            "                    \"name\": \"P. Blache\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"1678451\",\n" +
            "                    \"name\": \"K. Choukri\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2147401\",\n" +
            "                    \"name\": \"C. Cieri\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"72836788\",\n" +
            "                    \"name\": \"T. Declerck\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2612444\",\n" +
            "                    \"name\": \"S. Goggi\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"1714134\",\n" +
            "                    \"name\": \"H. Isahara\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2021893\",\n" +
            "                    \"name\": \"B. Maegaard\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"144870387\",\n" +
            "                    \"name\": \"J. Mariani\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2347502\",\n" +
            "                    \"name\": \"H. Mazo\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2152222170\",\n" +
            "                    \"name\": \"A.\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2075515808\",\n" +
            "                    \"name\": \"Moreno\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"113532881\",\n" +
            "                    \"name\": \"J. Odijk\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2954279\",\n" +
            "                    \"name\": \"Stelios Piperidis\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"paperId\": \"a85f8b366f368bfef168e2090adb33a9bbafcb7d\",\n" +
            "            \"url\": \"https://www.semanticscholar.org/paper/a85f8b366f368bfef168e2090adb33a9bbafcb7d\",\n" +
            "            \"title\": \"UvA-DARE (Digital Academic Repository) Towards Entity Spaces Towards Entity Spaces\",\n" +
            "            \"abstract\": \"Entities are a central element of knowledge bases and are important input to many knowledge-centric tasks including text analysis. For example, they allow us to ﬁnd documents relevant to a speciﬁc entity irrespective of the underlying syntactic expression within a document. However, the entities that are commonly represented in knowledge bases are often a simpliﬁcation of what is truly being referred to in text. For example, in a knowledge base, we may have an entity for Germany as a country but not for the more fuzzy concept of Germany that covers notions of German Population, German Drivers, and the German Government. Inspired by recent advances in contextual word embeddings, we introduce the concept of entity spaces - speciﬁc representations of a set of associated entities with near-identity. Thus, these entity spaces provide a handle to an amorphous grouping of entities. We developed a proof-of-concept for English showing how, through the introduction of entity spaces in the form of disambiguation pages, the recall of entity linking can be improved.\",\n" +
            "            \"venue\": \"\",\n" +
            "            \"year\": 2020,\n" +
            "            \"referenceCount\": 35,\n" +
            "            \"citationCount\": 0,\n" +
            "            \"authors\": [\n" +
            "                {\n" +
            "                    \"authorId\": \"2064426690\",\n" +
            "                    \"name\": \"F. Béchet\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2652453\",\n" +
            "                    \"name\": \"P. Blache\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"1678451\",\n" +
            "                    \"name\": \"K. Choukri\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2147401\",\n" +
            "                    \"name\": \"C. Cieri\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"72836788\",\n" +
            "                    \"name\": \"T. Declerck\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2612444\",\n" +
            "                    \"name\": \"S. Goggi\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"1714134\",\n" +
            "                    \"name\": \"H. Isahara\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2021893\",\n" +
            "                    \"name\": \"B. Maegaard\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"144870387\",\n" +
            "                    \"name\": \"J. Mariani\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2347502\",\n" +
            "                    \"name\": \"H. Mazo\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2152222170\",\n" +
            "                    \"name\": \"A.\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2075515808\",\n" +
            "                    \"name\": \"Moreno\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"113532881\",\n" +
            "                    \"name\": \"J. Odijk\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2954279\",\n" +
            "                    \"name\": \"Stelios Piperidis\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"paperId\": \"4538fd99ea0fce7bc255985046a9890312d6d1ed\",\n" +
            "            \"url\": \"https://www.semanticscholar.org/paper/4538fd99ea0fce7bc255985046a9890312d6d1ed\",\n" +
            "            \"title\": \"The Largest Knowledge Graph in Materials Science Entities, Relations, and Link Prediction through Graph Representation Learning\",\n" +
            "            \"abstract\": \"This paper introduces MatKG, a novel graph database of key concepts in material 1 science spanning the traditional material-structure-property-processing paradigm. 2 MatKG is autonomously generated through transformer-based, large language 3 models and generates pseudo ontological schema through statistical co-occurrence 4 mapping. At present, MatKG contains over 2 million unique relationship triples 5 derived from 80,000 entities. This allows the curated analysis, querying, and 6 visualization of materials knowledge at unique resolution and scale. Further, 7 Knowledge Graph Embedding models are used to learn embedding representations 8 of nodes in the graph which are used for downstream tasks such as link prediction 9 and entity disambiguation. MatKG allows the rapid dissemination and assimilation 10 of data when used as a knowledge base, while enabling the discovery of new 11 relations when trained as an embedding model.\",\n" +
            "            \"venue\": \"\",\n" +
            "            \"year\": 2022,\n" +
            "            \"referenceCount\": 28,\n" +
            "            \"citationCount\": 0,\n" +
            "            \"authors\": [\n" +
            "                {\n" +
            "                    \"authorId\": \"23138704\",\n" +
            "                    \"name\": \"John Dagdelen\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"144979831\",\n" +
            "                    \"name\": \"Alex Dunn\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"144503015\",\n" +
            "                    \"name\": \"O. Kononova\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"144431479\",\n" +
            "                    \"name\": \"Mary Brady\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"9730842\",\n" +
            "                    \"name\": \"C. Campbell\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2193532542\",\n" +
            "                    \"name\": \"Arthur P Ramirez\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2193465627\",\n" +
            "                    \"name\": \"Lars PE Yunker\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"paperId\": \"cd47cc6b4cfa460c01250acfad5ec95b4e7db9df\",\n" +
            "            \"url\": \"https://www.semanticscholar.org/paper/cd47cc6b4cfa460c01250acfad5ec95b4e7db9df\",\n" +
            "            \"title\": \"Towards Improving the Explainability of Text-based Information Retrieval with Knowledge Graphs\",\n" +
            "            \"abstract\": \"Thanks to recent advancements in machine learning, vector-based methods have been adopted in many modern information retrieval (IR) systems. While showing promising retrieval performance, these approaches typically fail to explain why a particular document is retrieved as a query result to address explainable information retrieval (XIR). Knowledge graphs record structured information about entities and inherently explainable relationships. Most of existing XIR approaches focus exclusively on the retrieval model with little consideration on using existing knowledge graphs for providing an explanation. In this paper, we propose a general architecture to incorporate knowledge graphs for XIR in various steps of the retrieval process. Furthermore, we create two instances of the architecture for different types of explanation. We evaluate our approaches on well-known IR benchmarks using standard metrics and compare them with vector-based methods as baselines.\",\n" +
            "            \"venue\": \"ArXiv\",\n" +
            "            \"year\": 2023,\n" +
            "            \"referenceCount\": 46,\n" +
            "            \"citationCount\": 0,\n" +
            "            \"authors\": [\n" +
            "                {\n" +
            "                    \"authorId\": \"2882606\",\n" +
            "                    \"name\": \"Boqi Chen\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"145578365\",\n" +
            "                    \"name\": \"Kuan-Yu Chen\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"3001727\",\n" +
            "                    \"name\": \"Yujiu Yang\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2201327459\",\n" +
            "                    \"name\": \"Afshin Amini\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2201329609\",\n" +
            "                    \"name\": \"Bharat Saxena\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2201319560\",\n" +
            "                    \"name\": \"Cecilia Ch'avez-Garc'ia\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2587928\",\n" +
            "                    \"name\": \"Majid Babaei\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"15845853\",\n" +
            "                    \"name\": \"A. Feizpour\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"71971228\",\n" +
            "                    \"name\": \"Dániel Varró\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"paperId\": \"5cb13825a91e1dfbe3d2890c4c2c62affacc4ecd\",\n" +
            "            \"url\": \"https://www.semanticscholar.org/paper/5cb13825a91e1dfbe3d2890c4c2c62affacc4ecd\",\n" +
            "            \"title\": \"Pylon: Semantic Table Union Search in Data Lakes\",\n" +
            "            \"abstract\": \"—The large size and fast growth of data repositories, such as data lakes, has spurred the need for data discovery to help analysts ﬁnd related data. The problem has become challenging as (i) a user typically does not know what datasets exist in an enormous data repository; and (ii) there is usually a lack of a uniﬁed data model to capture the interrelationships between heterogeneous datasets from disparate sources. In this work, we address one important class of discovery needs: ﬁnding union-able tables. The task is to ﬁnd tables in a data lake that can be unioned with a given query table. The challenge is to recognize union- able columns even if they are represented differently. In this paper, we propose a data-driven learning approach: speciﬁcally, an unsupervised representation learning and embedding retrieval task. Our key idea is to exploit self-supervised contrastive learning to learn an embedding model that takes into account the indexing/search data structure and produces embeddings close by for columns with semantically similar values while pushing apart columns with semantically dissimilar values. We then ﬁnd union-able tables based on similarities between their constituent columns in embedding space. On a real-world data lake, we demonstrate that our best-performing model achieves signiﬁcant improvements in precision ( 16% ↑ ), recall ( 17% ↑ ), and query response time (7x faster) compared to the state-of-the-art.\",\n" +
            "            \"venue\": \"ArXiv\",\n" +
            "            \"year\": 2023,\n" +
            "            \"referenceCount\": 35,\n" +
            "            \"citationCount\": 1,\n" +
            "            \"authors\": [\n" +
            "                {\n" +
            "                    \"authorId\": \"1432234356\",\n" +
            "                    \"name\": \"Tianji Cong\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2315516\",\n" +
            "                    \"name\": \"F. Nargesian\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"145531067\",\n" +
            "                    \"name\": \"H. Jagadish\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"paperId\": \"e7b2f6960aa1eb34cb84af01f23df7ce230f1a95\",\n" +
            "            \"url\": \"https://www.semanticscholar.org/paper/e7b2f6960aa1eb34cb84af01f23df7ce230f1a95\",\n" +
            "            \"title\": \"Scientific Paper Extractive Summarization Enhanced by Citation Graphs\",\n" +
            "            \"abstract\": \"In a citation graph, adjacent paper nodes share related scientiﬁc terms and topics. The graph thus conveys unique structure information of document-level relatedness that can be utilized in the paper summarization task, for explor-ing beyond the intra-document information. In this work, we focus on leveraging citation graphs to improve scientiﬁc paper extractive summarization under different settings. We ﬁrst propose a Multi-granularity Unsupervised Summarization model (MUS) as a simple and low-cost solution to the task. MUS ﬁnetunes a pre-trained encoder model on the citation graph by link prediction tasks. Then, the abstract sentences are extracted from the corresponding paper considering multi-granularity information. Preliminary results demonstrate that citation graph is helpful even in a simple unsupervised framework. Motivated by this, we next propose a Graph-based Supervised Summarization model (GSS) to achieve more accurate results on the task when large-scale labeled data are available. Apart from employing the link prediction as an auxiliary task, GSS introduces a gated sentence encoder and a graph information fusion module to take advantage of the graph information to polish the sentence representation. Experiments on a public benchmark dataset show that MUS and GSS bring substantial improvements over the prior state-of-the-art model.\",\n" +
            "            \"venue\": \"ArXiv\",\n" +
            "            \"year\": 2022,\n" +
            "            \"referenceCount\": 31,\n" +
            "            \"citationCount\": 0,\n" +
            "            \"authors\": [\n" +
            "                {\n" +
            "                    \"authorId\": \"46772896\",\n" +
            "                    \"name\": \"Xiuying Chen\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2135609879\",\n" +
            "                    \"name\": \"Mingzhe Li\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2112311595\",\n" +
            "                    \"name\": \"Shen Gao\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2055863987\",\n" +
            "                    \"name\": \"Rui Yan\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2118502950\",\n" +
            "                    \"name\": \"Xin Gao\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2928371\",\n" +
            "                    \"name\": \"Xiangliang Zhang\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "    ]\n" +
            "}";

    @Test
    void convertRecommendations() throws JsonProcessingException, JSONException {
        new SemanticScholarWrapper().convertRecommendations(jsonRecommendations);

    }

    @Test
    void convertCitationsReferences() throws JSONException, JsonProcessingException {
        new SemanticScholarWrapper().convertCitationsReferencesSearch(jsonReferences);
    }

    @Test
    void convertToPaper() throws JSONException, JsonProcessingException {
        List<ApiPaper> paper = new SemanticScholarWrapper().convertToPaper(jsonPaper);
        for (int i = 0; i < paper.get(0).getAuthors().size(); i++) {
            System.out.println(paper.get(0).getAuthors().get(i).getName());
            System.out.println(paper.get(0).getAuthors().get(i).getId());
        }

    }
}