package com.example.referentiel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.example.referentiel.model.Account;
import com.example.referentiel.model.Ami;
import com.example.referentiel.model.Az;
import com.example.referentiel.model.Cidr;
import com.example.referentiel.model.Efs;
import com.example.referentiel.model.ElasticCache;
import com.example.referentiel.model.ElasticSearch;
import com.example.referentiel.model.InstanceType;
import com.example.referentiel.model.Lb;
import com.example.referentiel.model.Listener;
import com.example.referentiel.model.Nacl;
import com.example.referentiel.model.Peering;
import com.example.referentiel.model.PeeringAccepterExternal;
import com.example.referentiel.model.PeeringAccepterInternal;
import com.example.referentiel.model.Product;
import com.example.referentiel.model.Rds;
import com.example.referentiel.model.Region;
import com.example.referentiel.model.Route;
import com.example.referentiel.model.RouteTable;
import com.example.referentiel.model.Rule;
import com.example.referentiel.model.RuleSg;
import com.example.referentiel.model.Sg;
import com.example.referentiel.model.Subnet;
import com.example.referentiel.model.SubnetCidr;
import com.example.referentiel.model.SubnetGroup;
import com.example.referentiel.model.Tag;
import com.example.referentiel.model.Target;
import com.example.referentiel.model.TargetGroup;
import com.example.referentiel.model.Trigramme;
import com.example.referentiel.model.Vpc;
import com.example.referentiel.repository.AccountRepository;
import com.example.referentiel.repository.AmiRepository;
import com.example.referentiel.repository.AzRepository;
import com.example.referentiel.repository.CidrRepository;
import com.example.referentiel.repository.EfsRepository;
import com.example.referentiel.repository.ElasticCacheRepository;
import com.example.referentiel.repository.ElasticSearchRepository;
import com.example.referentiel.repository.InstanceTypeRepository;
import com.example.referentiel.repository.LbRepository;
import com.example.referentiel.repository.ListenerRepository;
import com.example.referentiel.repository.NaclRepository;
import com.example.referentiel.repository.PeeringAccepterExternalRepository;
import com.example.referentiel.repository.PeeringAccepterInternalRepository;
import com.example.referentiel.repository.PeeringRepository;
import com.example.referentiel.repository.ProductRepository;
import com.example.referentiel.repository.RdsRepository;
import com.example.referentiel.repository.RegionRepository;
import com.example.referentiel.repository.RouteRepository;
import com.example.referentiel.repository.RouteTableRepository;
import com.example.referentiel.repository.RuleRepository;
import com.example.referentiel.repository.RuleSgRepository;
import com.example.referentiel.repository.SgRepository;
import com.example.referentiel.repository.SubnetCidrRepository;
import com.example.referentiel.repository.SubnetGroupRepository;
import com.example.referentiel.repository.SubnetRepository;
import com.example.referentiel.repository.TagRepository;
import com.example.referentiel.repository.TargetGroupRepository;
import com.example.referentiel.repository.TargetRepository;
import com.example.referentiel.repository.TrigrammeRepository;
import com.example.referentiel.repository.VpcRepository;

@SpringBootApplication
@EnableJpaAuditing
@Transactional
public class ReferentielApplication implements CommandLineRunner{
	
	 
	public static void main(String[] args) {
		SpringApplication.run(ReferentielApplication.class, args);
	}
	
	@Autowired
    private RegionRepository regionRepository;
    
	@Autowired
    private AzRepository azRepository;
	
	@Autowired
    private CidrRepository cidrRepository;
	
	@Autowired
    private SubnetCidrRepository subnetCidrRepository;
	
	@Autowired
    private TrigrammeRepository trigrammeRepository;
	
	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
    private AccountRepository accountRepository;
	
	@Autowired
    private VpcRepository vpcRepository;
	
	@Autowired
    private SubnetRepository subnetRepository;
	
	@Autowired
    private SubnetGroupRepository subnetGroupRepository;
	
	@Autowired
    private RdsRepository rdsRepository;
	
	@Autowired
    private EfsRepository efsRepository;
	
	@Autowired
    private ElasticCacheRepository elasticCacheRepository;
	
	@Autowired
    private ElasticSearchRepository elasticSearchRepository;
	
	@Autowired
    private NaclRepository naclRepository;
	
	@Autowired
    private RuleRepository ruleRepository;
	
	@Autowired
    private TagRepository tagRepository;
	
	@Autowired
    private SgRepository sgRepository;
	
	@Autowired
    private RuleSgRepository ruleSgRepository;
	
	@Autowired
    private RouteRepository routeRepository;
	
	@Autowired
    private RouteTableRepository routeTableRepository;
	
	@Autowired
    private PeeringRepository peeringRepository;
	
	@Autowired
    private PeeringAccepterExternalRepository peeringAccepterExternalRepository;
	
	@Autowired
    private PeeringAccepterInternalRepository peeringAccepterInternalRepository;
	
	@Autowired
    private TargetGroupRepository targetGroupRepository;
	
	@Autowired
    private TargetRepository targetRepository;
	
	@Autowired
    private ListenerRepository listenerRepository;
	
	@Autowired
    private LbRepository lbRepository;
	  
	@Autowired
    private InstanceTypeRepository instanceTypeRepository;
	
	@Autowired
    private AmiRepository amiRepository;
	
	 @Override
	 public void run(String... args) throws Exception {
		 
		InstanceType instanceType = new InstanceType();
		instanceType.setFamily("001");
     	instanceType.setType("001");
     	instanceType.setVcpus(1l);
     	instanceType.setMemory(1l);
     	instanceType.setInstanceStorage("001");
     	instanceType.setEbsOptimized(true);
     	instanceType.setNetworkPerformance("001");
     	instanceType = instanceTypeRepository.save(instanceType);
     	
		 Region  region = new Region();
		 region.setName("11");
		 region.setDescription("desc");
		 Region r = regionRepository.save(region);
		 
		 Ami ami = new Ami();
		 ami.setAmiId("001");
     	 ami.setName("001");
     	 ami.setText("001");
     	 ami.setRegion(r);
     	 ami = amiRepository.save(ami);
     	 
		 Az az = new Az();
		 az.setName("az001");
		 az.setDescription("az001");
		 az.setRegion(r);
		 Az az1 = azRepository.save(az);
		 az = new Az();
		 az.setName("az002");
		 az.setDescription("az002");
		 az.setRegion(r);
		 Az az2 = azRepository.save(az);
		 az = new Az();
		 az.setName("az003");
		 az.setDescription("az003");
		 az.setRegion(r);
		 Az az3 = azRepository.save(az);
		 
		 Cidr cidr = new Cidr();
		 cidr.setCidr("10.10.10.10/24");
		 cidr.setEnv("DEV");
		 cidr.setText("10.10.10.10/24 DEV");
		 cidr.setRegion(r);
		 Cidr c = cidrRepository.save(cidr);
		 
		 SubnetCidr subnetCidr = new  SubnetCidr();
		 subnetCidr.setCidr(c);
		 subnetCidr.setSubnetCidr("10.10.10.10/25");
		 subnetCidr.setText("10.10.10.10/25");
		 SubnetCidr sc1 = subnetCidrRepository.save(subnetCidr);
		 subnetCidr = new  SubnetCidr();
		 subnetCidr.setCidr(c);
		 subnetCidr.setSubnetCidr("10.10.10.10/26");
		 subnetCidr.setText("10.10.10.10/26");
		 SubnetCidr sc2 = subnetCidrRepository.save(subnetCidr);
		 subnetCidr = new  SubnetCidr();
		 subnetCidr.setCidr(c);
		 subnetCidr.setSubnetCidr("10.10.10.10/27");
		 subnetCidr.setText("10.10.10.10/27");
		 SubnetCidr sc3 = subnetCidrRepository.save(subnetCidr);
		 
		 
		 Trigramme trigramme = new Trigramme();
		 trigramme.setName("TRI");
		 trigramme.setDescription("tri");
		 trigramme.setIrtCode("12345");
		 trigramme.setMailList("cc@cc.com");
		 trigramme.setOwner("oo@oo.com");
		 Trigramme t = trigrammeRepository.save(trigramme);
		 
		 Product product = new Product();
		 product.setName("PRD");
		 product.setMailList("mm@mm.com");
		 product.setType("ttt");
		 product.setTrigramme(t);
		 product.setBastion("bastion");
		 Product p = productRepository.save(product);
		 
		 Account account = new Account();
		 account.setNumAccount("123456789012");
		 account.setEnv("DEV");
		 //account.setRegion(r);
		 account.setProduct(p);
		 account.setMailList("aa@aa.com");
		 account.setAlias("alias");
		 Account a = accountRepository.save(account);
		 
		 
		 Vpc vpc = new Vpc();
		 vpc.setAccount(a);
		 vpc.setCidr(c);
		 vpc.setName("111");
		 Vpc v = vpcRepository.save(vpc);
		 
		 
		 Subnet subnet = new Subnet();
		 subnet.setName("s001");
		 subnet.setType("ttt");
		 subnet.setVpc(v);
		 subnet.setsCidr(sc1);
		 subnet.setAz(az1);
		 Subnet s1 = subnetRepository.save(subnet);
		 
		 subnet = new Subnet();
		 subnet.setName("s002");
		 subnet.setType("ttt");
		 subnet.setVpc(v);
		 subnet.setsCidr(sc2);
		 subnet.setAz(az2);
		 Subnet s2 = subnetRepository.save(subnet);
		 
		 subnet = new Subnet();
		 subnet.setName("s003");
		 subnet.setType("ttt");
		 subnet.setVpc(v);
		 subnet.setsCidr(sc3);
		 subnet.setAz(az3);
		 Subnet s3 = subnetRepository.save(subnet);
		 
		 
		 //
		 SubnetGroup subnetGroup = new SubnetGroup();
		 subnetGroup.setVpc(v);
		 subnetGroup.setName("sg001");
		 subnetGroup.setType("RDS");
		 
		 List<Subnet> subnets = new ArrayList<>(); 
		 subnets.add(s1);
		 subnets.add(s2);
		 subnets.add(s3);
		 
		 subnetGroup.setSubnets(subnets);
		 SubnetGroup sg = subnetGroupRepository.save(subnetGroup);
		 s1.getSubnetgroup().add(sg);
		 s2.getSubnetgroup().add(sg);
		 s3.getSubnetgroup().add(sg);
		 s1 = subnetRepository.save(s1);
		 s2 = subnetRepository.save(s2);
		 s3 = subnetRepository.save(s3);
		 
		 
		 Optional<SubnetGroup> sgd = subnetGroupRepository.findById((long) 1000);
		 Iterator<Subnet> itt = sgd.get().getSubnets().iterator();
		 while(itt.hasNext()) {
			Subnet sbb = (Subnet)itt.next();
			//System.out.println("subnet after:" + sbb.getId());
		 }
		 
		 Rds rds = new Rds();
		 rds.setName("R001");
		 rds.setText("R001");
		 rds.setVpc(v);
		 rds.setSubnetgroup(sg);
		 Rds rds1 = rdsRepository.save(rds);
		 Optional<Rds> rd = rdsRepository.findById(rds1.getId());
		 //System.out.println("Rds after:" + rd.get().getSubnetgroup().getName());
		 Optional<SubnetGroup> sgg = subnetGroupRepository.findById(sg.getId());
		 Iterator<Rds> itg = sgg.get().getRdss().iterator();
		 while(itg.hasNext()) {
			Rds rr = (Rds)itg.next();
			System.out.println("Rds after:" + rr.getId());
		 }
		 
		 subnetGroup = new SubnetGroup();
		 subnetGroup.setVpc(v);
		 subnetGroup.setName("sg002");
		 subnetGroup.setType("EFS");
		 s1.getSubnetgroup().add(subnetGroup);
		 s2.getSubnetgroup().add(subnetGroup);
		 s3.getSubnetgroup().add(subnetGroup);
		 subnets = new ArrayList<>(); 
		 subnets.add(s1);
		 subnets.add(s2);
		 subnets.add(s3);
		 
		 subnetGroup.setSubnets(subnets);
		 sg = subnetGroupRepository.save(subnetGroup);
		 
		 s1 = subnetRepository.save(s1);
		 s2 = subnetRepository.save(s2);
		 s3 = subnetRepository.save(s3);
		 //
		 Efs efs = new Efs();
		 efs.setName("EFS001");
		 efs.setText("EFS001");
		 efs.setVpc(v);
		 efs.setSubnetgroup(sg);
		 
		 Efs efs1 = efsRepository.save(efs);
		 
		 //
		 subnetGroup = new SubnetGroup();
		 subnetGroup.setVpc(v);
		 subnetGroup.setName("sg003");
		 subnetGroup.setType("ECC");
		 s1.getSubnetgroup().add(subnetGroup);
		 s2.getSubnetgroup().add(subnetGroup);
		 s3.getSubnetgroup().add(subnetGroup);
		 subnets = new ArrayList<>(); 
		 subnets.add(s1);
		 subnets.add(s2);
		 subnets.add(s3);
		 
		 subnetGroup.setSubnets(subnets);
		 sg = subnetGroupRepository.save(subnetGroup);
		 
		 s1 = subnetRepository.save(s1);
		 s2 = subnetRepository.save(s2);
		 s3 = subnetRepository.save(s3);
		 
		 ElasticCache elasticCache = new ElasticCache();
		 elasticCache.setName("ElasticCache001");
		 elasticCache.setText("ElasticCache001");
		 elasticCache.setVpc(v);
		 elasticCache.setSubnetgroup(sg);
		 ElasticCache elasticCache1 = elasticCacheRepository.save(elasticCache);
		 
		 //
		 subnetGroup = new SubnetGroup();
		 subnetGroup.setVpc(v);
		 subnetGroup.setName("sg004");
		 subnetGroup.setType("ELK");
		 s1.getSubnetgroup().add(subnetGroup);
		 s2.getSubnetgroup().add(subnetGroup);
		 s3.getSubnetgroup().add(subnetGroup);
		 subnets = new ArrayList<>(); 
		 subnets.add(s1);
		 subnets.add(s2);
		 subnets.add(s3);
		 
		 subnetGroup.setSubnets(subnets);
		 sg = subnetGroupRepository.save(subnetGroup);
		 
		 s1 = subnetRepository.save(s1);
		 s2 = subnetRepository.save(s2);
		 s3 = subnetRepository.save(s3);
		 
		 
		 ElasticSearch elasticSearch = new ElasticSearch();
		 elasticSearch.setName("ElasticSearch001");
		 elasticSearch.setText("ElasticSearch001");
		 elasticSearch.setPrive(true);
		 elasticSearch.setVpc(v);
		 elasticSearch.setSubnetgroup(sg);
		 
		 ElasticSearch elasticSearch1 = elasticSearchRepository.save(elasticSearch);
		 
		 elasticSearch = new ElasticSearch();
		 elasticSearch.setName("ElasticSearch002");
		 elasticSearch.setText("ElasticSearch002");
		 elasticSearch.setPrive(false);
		 //elasticSearch.setVpc(v);
		 //elasticSearch.setSubnetgroup(sg);
		 
		 elasticSearch1 = elasticSearchRepository.save(elasticSearch);
		 
		 
		 Nacl nacl = new Nacl();
		 nacl.setName("Nacl001");
		 nacl.setText("Nacl001");
		 nacl.setVpc(v);
		 nacl.setDef(false);
		 
		 subnets = new ArrayList<>(); 
		 subnets.add(s1);
		 subnets.add(s2);
		 subnets.add(s3);
		 nacl.setSubnets(subnets);
		 s1.getNacls().add(nacl);
		 s2.getNacls().add(nacl);
		 s3.getNacls().add(nacl);
		 
		 Nacl nacl1 = naclRepository.save(nacl);
		 
		 s1 = subnetRepository.save(s1);
		 s2 = subnetRepository.save(s2);
		 s3 = subnetRepository.save(s3);
		 
		 //ajout des subnets assoc
		 Optional<Nacl> nac = naclRepository.findById(nacl1.getId());
		 Iterator<Subnet> itnac = nac.get().getSubnets().iterator();
		 while(itnac.hasNext()) {
			Subnet sub = (Subnet)itnac.next();
			System.out.println("Nac subnet after:" + sub.getId() + " " + sub.getName());
		 }
		 Optional<Subnet> sub = subnetRepository.findById(s1.getId());
		 Iterator<Nacl> itsub = sub.get().getNacls().iterator();
		 while(itsub.hasNext()) {
			Nacl nacc = (Nacl)itsub.next();
			System.out.println("Nac subnet after 2:" + nacc.getId() + " " + nacc.getName());
		 }
		 
		 //
		 Tag tag = new Tag();
		 tag.setKey("key 001");
		 tag.setValue("value 001");
		 tag.setNacl(nacl1);
		 Tag tag1 = tagRepository.save(tag);
		 tag1 = new Tag();
		 tag1.setKey("key 002");
		 tag1.setValue("value 002");
		 tag1.setNacl(nacl1);
		 tag1 = tagRepository.save(tag1);
		 //	 
		 
		 Rule rule = new Rule();
		 rule.setText("Rule001");
		 rule.setNacl(nacl1);
		 rule.setType("INBOUND");
		 rule.setNumber("*");
     	 rule.setRuleType("ALL Traffic");
     	 rule.setProtocol("HTTPS");
     	 rule.setPortRange("80");
     	 rule.setCidr("10.10.10.10/24");
     	 rule.setAllow("Deny");
     	
		 Rule rule1 = ruleRepository.save(rule);
		 
		 Sg secg = new Sg();
		 secg.setName("Sg001");
		 secg.setNameTag("Sg001 tag");
		 secg.setText("Sg001");
		 secg.setVpc(v);
		 Sg secg1 = sgRepository.save(secg);
		 
		 
		 Tag tag2 = new Tag();
		 tag2.setKey("key sg 001");
		 tag2.setValue("value sg 001");
		 tag2.setSg(secg1);
		 Tag tag3 = tagRepository.save(tag2);
		 
		 tag3 = new Tag();
		 tag3.setKey("key sg 002");
		 tag3.setValue("value sg 002");
		 tag3.setSg(secg1);
		 tag3 = tagRepository.save(tag3);
		 
		 RuleSg ruleSg = new RuleSg();
		 ruleSg.setText("Rule001");
		 ruleSg.setSg(secg1);
		 ruleSg.setType("INBOUND");
		 ruleSg.setRuleType("ALL Traffic");
     	 ruleSg.setProtocol("HTTPS");
     	 ruleSg.setPortRange("80");
     	 ruleSg.setCidr("10.10.10.10/24");
     	 
     	
		 RuleSg ruleSg1 = ruleSgRepository.save(ruleSg);
		 
		 //routetabe
		 
		 RouteTable routeTable = new RouteTable();
		 routeTable.setName("NRouteTable001");
		 routeTable.setText("RouteTable001");
		 routeTable.setVpc(v);
		 routeTable.setDef(false);
		 
		 subnets = new ArrayList<>(); 
		 subnets.add(s1);
		 subnets.add(s2);
		 subnets.add(s3);
		 routeTable.setSubnets(subnets);
		 s1.getRoutetables().add(routeTable);
		 s2.getRoutetables().add(routeTable);
		 s3.getRoutetables().add(routeTable);
		 
		 RouteTable routeTable1 = routeTableRepository.save(routeTable);
		 
		 s1 = subnetRepository.save(s1);
		 s2 = subnetRepository.save(s2);
		 s3 = subnetRepository.save(s3);
		 
		 //ajout des subnets assoc
		 Optional<RouteTable> routet = routeTableRepository.findById(routeTable1.getId());
		 Iterator<Subnet> itrt = routet.get().getSubnets().iterator();
		 while(itrt.hasNext()) {
			Subnet subrt = (Subnet)itrt.next();
			System.out.println("Routetable subnet after:" + subrt.getId() + " " + subrt.getName());
		 }
		 Optional<Subnet> subrt = subnetRepository.findById(s1.getId());
		 Iterator<RouteTable> itrts = subrt.get().getRoutetables().iterator();
		 while(itsub.hasNext()) {
			RouteTable rt = (RouteTable)itrts.next();
			System.out.println("RouteTable subnet after 2:" + rt.getId() + " " + rt.getName());
		 }
		 
		 //
		 tag = new Tag();
		 tag.setKey("key 001");
		 tag.setValue("value 001");
		 tag.setRouteTable(routeTable1);
		 tag1 = tagRepository.save(tag);
		 tag1 = new Tag();
		 tag1.setKey("key 002");
		 tag1.setValue("value 002");
		 tag1.setRouteTable(routeTable1);
		 tag1 = tagRepository.save(tag1);
		 //	 
		 
		 Route route = new Route();
		 route.setText("Rule001");
		 route.setRouteTable(routeTable1);
		 route.setPropagation(false);
		 route.setDestination("10.10.10.10/24");
		 route.setTarget("Local");
     	
		 Route route1 = routeRepository.save(route);
		  
		 
		 //Peering
		 
		 Peering peering = new Peering();
		 peering.setName("PEERING001");
		 peering.setText("PEERING001");
		 peering.setType("External");
		 peering.setVpc(v);
		 
		 PeeringAccepterExternal peeringAccepterExternal = new PeeringAccepterExternal();
		 peeringAccepterExternal.setOwner("peeringAccepterExternal001");
		 peeringAccepterExternal.setVpcId("10.10.10.10/24");
		 peeringAccepterExternal.setRegion(r);
		 peeringAccepterExternal.setPeering(peering);
		 peering.setPeeringAccepterExternal(peeringAccepterExternal);
		 
		 
		 peeringAccepterExternal = peeringAccepterExternalRepository.save(peeringAccepterExternal);
		 peering = peeringRepository.save(peering);
		 
		 
		 peering = new Peering();
		 peering.setName("PEERING002");
		 peering.setText("PEERING002");
		 peering.setType("Internal");
		 peering.setVpc(v);
		 PeeringAccepterInternal peeringAccepterInternal = new PeeringAccepterInternal();
		 peeringAccepterInternal.setVpc(v);
		 peeringAccepterInternal.setPeering(peering);
		 peering.setPeeringAccepterInternal(peeringAccepterInternal);
		 
		 peeringAccepterInternal = peeringAccepterInternalRepository.save(peeringAccepterInternal);
		 peering = peeringRepository.save(peering);
		 
		 tag = new Tag();
		 tag.setKey("key peering 001");
		 tag.setValue("value peering 001");
		 tag.setPeering(peering);
		 tag1 = tagRepository.save(tag);
		 tag1 = new Tag();
		 tag1.setKey("key peering 002");
		 tag1.setValue("value peering 002");
		 tag1.setPeering(peering);
		 tag1 = tagRepository.save(tag1);
		 
		 //TargetGroup + listener + loadbalancer
		
		 TargetGroup targetGroup = new TargetGroup();
		 targetGroup.setName("name targetGroup 001");
	     targetGroup.setText("text targetGroup 001");
	     	            
	     targetGroup.setProtocole("HTTPS");
	     targetGroup.setPort((long) 443);
	     targetGroup.setHcprotocole("HTTP");
	     targetGroup.setHcpath("/");
	     targetGroup.setType("instance");
	     targetGroup.setAhportoverride(false);
	     targetGroup.setAhport((long) 443);
	     targetGroup.setAhhealthythreshold((long) 5);
	     targetGroup.setAhuhealthythreshold((long) 2);
	     
	     targetGroup.setAhtimeout((long) 5);
	     targetGroup.setAhtinterval((long) 30);
	     targetGroup.setAhsucesscode("200-400");
	     targetGroup.setVpc(v);
	     targetGroup = targetGroupRepository.save(targetGroup);
	     
	     TargetGroup targetGroup2 = new TargetGroup(); 
	     targetGroup2.setName("name targetGroup 002");
	     targetGroup2.setText("text targetGroup 002");
	     	            
	     targetGroup2.setProtocole("HTTPS");
	     targetGroup2.setPort((long) 443);
	     targetGroup2.setHcprotocole("HTTP");
	     targetGroup2.setHcpath("/");
	     targetGroup2.setType("instance");
	     targetGroup2.setAhportoverride(true);
	     targetGroup2.setAhport((long) 80);
	     targetGroup2.setAhhealthythreshold((long) 5);
	     targetGroup2.setAhuhealthythreshold((long) 2);
	     targetGroup2.setAhtimeout((long) 5);
	     targetGroup2.setAhtinterval((long) 30);
	     targetGroup2.setAhsucesscode("200-400");
	     targetGroup2.setVpc(v);
	     targetGroup2 = targetGroupRepository.save(targetGroup2);
	     
	     tag = new Tag();
		 tag.setKey("key 001");
		 tag.setValue("value 001");
		 tag.setTargetGroup(targetGroup);
		 tag1 = tagRepository.save(tag);
		 tag1 = new Tag();
		 tag1.setKey("key 002");
		 tag1.setValue("value 002");
		 tag1.setTargetGroup(targetGroup2);
		 tag1 = tagRepository.save(tag1);
		 //
		 
	     Target target = new Target();
	     target.setPort((long) 80);
	     target.setEc2("machine 1 ec2");
	     target.setTargetGroup(targetGroup);
	     target = targetRepository.save(target);
	     
	     target = new Target();
	     target.setPort((long) 81);
	     target.setEc2("machine 2 ec2");
	     target.setTargetGroup(targetGroup);
	     target = targetRepository.save(target);
	     
	     target = new Target();
	     target.setPort((long) 88);
	     target.setEc2("machine 3 ec2");
	     target.setTargetGroup(targetGroup2);
	     target = targetRepository.save(target);
	     
	     
	     
	  //lb
	     Lb lb= new Lb();
	     lb.setName("Lb 001");
	     lb.setText("Lb 001");
	     lb.setType("ALB");
	     lb.setScheme(true);
	     lb.setVpc(v);
	     List<Subnet> subs = subnetRepository.findByVpcId(v.getId());
	     lb.setSubnets(subs);
	     lb.getSgs().add(secg1);
	     secg1.getLbs().add(lb);
	     for(int i = 0; i < subs.size(); i++){
	    	 Subnet sublb = (Subnet)subs.get(i);
	    	 sublb.getLbs().add(lb); 
	     }
	     lb = lbRepository.save(lb);
	     
	     
	     
	     lb= new Lb();
	     lb.setName("Lb 002");
	     lb.setText("Lb 002");
	     lb.setType("NLB");
	     lb.setScheme(true);
	     lb.setVpc(v);
	     subs = subnetRepository.findByVpcId(v.getId());
	     lb.setSubnets(subs);
	     for(int i = 0; i < subs.size(); i++){
	    	 Subnet sublb = (Subnet)subs.get(i);
	    	 sublb.getLbs().add(lb); 
	     }
	     lb = lbRepository.save(lb);
	   
	     lb= new Lb();
	     lb.setName("Lb 003");
	     lb.setText("Lb 003");
	     lb.setType("ELB");
	     lb.setScheme(true);
	     lb.setVpc(v);
	     subs = subnetRepository.findByVpcId(v.getId());
	     lb.setSubnets(subs);
	     for(int i = 0; i < subs.size(); i++){
	    	 Subnet sublb = (Subnet)subs.get(i);
	    	 sublb.getLbs().add(lb); 
	     }
	     lb = lbRepository.save(lb);
	     
	     lb= new Lb();
	     lb.setName("Lb 004");
	     lb.setText("Lb 004");
	     lb.setType("ALB");
	     lb.setIpType("ipv6");
	     lb.setScheme(false);
	     lb.setVpc(v);
	     subs = subnetRepository.findByVpcId(v.getId());
	     lb.setSubnets(subs);
	     for(int i = 0; i < subs.size(); i++){
	    	 Subnet sublb = (Subnet)subs.get(i);
	    	 sublb.getLbs().add(lb); 
	     }
	     lb = lbRepository.save(lb);
	     
	     lb= new Lb();
	     lb.setName("Lb 005");
	     lb.setText("Lb 005");
	     lb.setType("NLB");
	     lb.setScheme(false);
	     lb.setVpc(v);
	     subs = subnetRepository.findByVpcId(v.getId());
	     lb.setSubnets(subs);
	     for(int i = 0; i < subs.size(); i++){
	    	 Subnet sublb = (Subnet)subs.get(i);
	    	 sublb.getLbs().add(lb); 
	     }
	     lb = lbRepository.save(lb);
	     
	     lb= new Lb();
	     lb.setName("Lb 006");
	     lb.setText("Lb 006");
	     lb.setType("ELB");
	     lb.setScheme(false);
	     lb.setVpc(v);
	     subs = subnetRepository.findByVpcId(v.getId());
	     lb.setSubnets(subs);
	     for(int i = 0; i < subs.size(); i++){
	    	 Subnet sublb = (Subnet)subs.get(i);
	    	 sublb.getLbs().add(lb); 
	     }
	     lb = lbRepository.save(lb);
		 
	     Listener listener = new Listener();
	     listener.setProtocole("TCP");
	     listener.setPort((long) 80);
	     listener.setLb(lb);
	     
	     listener.setTargetGroup(targetGroup);
	     listener = listenerRepository.save(listener);
	     
	     //targetGroup.setListener(listener);
	     //targetGroup = targetGroupRepository.save(targetGroup);
	     Optional<Listener> listener2 = listenerRepository.findById((long) 1000);
	     TargetGroup tg = listener2.get().getTargetGroup();
	     if(tg != null)
	     {
	    	 System.out.println("TargetGroup=" + tg.getId());
	     } else {
	    	 System.out.println("TargetGroup null");
	     }
	     
	     
	     tag = new Tag();
		 tag.setKey("key 001");
		 tag.setValue("value 001");
		 tag.setLb(lb);
		 tag1 = tagRepository.save(tag);
		 tag1 = new Tag();
		 tag1.setKey("key 002");
		 tag1.setValue("value 002");
		 tag1.setLb(lb);
		 tag1 = tagRepository.save(tag1);
		 
		        
	 }
}
