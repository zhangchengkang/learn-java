
### doubbo启动流程
refresh-obtainFreshBeanFactory-refreshBeanFactory-loadBeanDefinitions-doLoadBeanDefinitions
-registerBeanDefinitions-doRegisterBeanDefinitions-parseBeanDefinitions-parseDefaultElement-parseCustomElement
NamespaceHandler handler = this.readerContext.getNamespaceHandlerResolver().resolve(namespaceUri);//需要在spring.handles配置dubboNamespaceHandler
namespaceHandler.init(){
registerBeanDefinitionParser("service", new DubboBeanDefinitionParser(ServiceBean.class, true));
registerBeanDefinitionParser("reference", new DubboBeanDefinitionParser(ReferenceBean.class, false));
};
DubboBeanDefinitionParser parse =   NamespaceHandlerSupport.findParserForElement
parse.parse//解析xml标签
serviveBean  serviceConfig  InitializingBean-afterPropertiesSet-publishEvent-onApplicationEvent-export-doExportUrls
-doExportUrlsFor1Protocol-protocol.export-RegistryProtocol.export-register-registry.register-zkregistry.register
referenceBean  referenceConfig  InitializingBean-afterPropertiesSet-getObject-get-init-createProxy-putToMap
