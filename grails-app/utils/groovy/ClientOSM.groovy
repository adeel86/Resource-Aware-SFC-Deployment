package groovy

import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import org.springframework.web.client.RestClientException
import resource_aware_sfc_deployment.NSInstance

class ClientOSM {

    RestBuilder restBuilder

    String url
    String username
    String password

    ClientOSM(String osmUrl, String osmUsername, String osmPassword) {

        restBuilder = new RestBuilder()
        url = osmUrl
        username = osmUsername
        password = osmPassword
    }

    RestResponse getBearerToken() {

        try {

            def res = restBuilder.get("$url/osm/admin/v1/tokens") {
                auth("$username", "$password")
                accept("application/json")
                contentType("application/json")
                header("ssl", "--insecure")
            }

            return res

        } catch (RestClientException error) {

            println("RestClientException" + error)
            return null
        }
    }

    RestResponse VNFDescriptor(String bearer) {

        try {

            def res = restBuilder.post("$url/osm/vnfpkgm/v1/vnf_packages_content") {
                contentType("application/json")
                accept("application/json")
                header("Bearer", $bearer)

                json("""{
                    {
                      "vnfd:vnfd-catalog": {
                        "vnfd": [
                          {
                            "connection-point": [
                              {
                                "name": "vnf-cp0",
                                "port-security-enabled": false,
                                "type": "VPORT"
                              }
                            ],
                            "description": "Generated by OSM package generator",
                            "id": "vnf_nsh_vnfd",
                            "mgmt-interface": {
                              "cp": "vnf-cp0"
                            },
                            "name": "vnf_nsh_vnfd",
                            "short-name": "vnf_nsh_vnfd",
                            "vdu": [
                              {
                                "count": 1,
                                "description": "vnf_nsh_vnfd-VM",
                                "id": "vnf_nsh_vnfd-VM",
                                "image": "sfc_nsh_fraser",
                                "interface": [
                                  {
                                    "external-connection-point-ref": "vnf-cp0",
                                    "name": "eth0",
                                    "type": "EXTERNAL",
                                    "virtual-interface": {
                                      "type": "PARAVIRT"
                                    }
                                  }
                                ],
                                "name": "vnf_nsh_vnfd-VM",
                                "vm-flavor": {
                                  "memory-mb": 1024,
                                  "storage-gb": 10,
                                  "vcpu-count": 1
                                }
                              }
                            ],
                            "vendor": "OSM",
                            "version": "1.0"
                          }
                        ]
                      }
                    }
                }"""
                )
            }

            return res

        } catch (RestClientException error) {

            println("RestClientException" + error)
            return null
        }
    }

    RestResponse NSDescriptor(String bearer) {

        try {

            def res = restBuilder.post("$url/osm/nsd/v1/ns_descriptors_content") {
                contentType("application/json")
                accept("application/json")
                header("Bearer", "$bearer")

                json("""{
                    {
                      "nsd:nsd-catalog": {
                        "nsd": [
                          {
                            "constituent-vnfd": [
                              {
                                "member-vnf-index": 1,
                                "vnfd-id-ref": "endpoint_vnfd"
                              },
                              {
                                "member-vnf-index": 2,
                                "vnfd-id-ref": "endpoint_vnfd"
                              },
                              {
                                "member-vnf-index": 3,
                                "vnfd-id-ref": "vnf_nsh_vnfd"
                              }
                            ],
                            "description": "Generated by OSM package generator",
                            "id": "3node_sfc_nsh_nsd",
                            "ip-profiles": [
                              {
                                "description": "IP network for external connectivity in Openstack 1 ",
                                "ip-profile-params": {
                                  "dhcp-params": {
                                    "count": 250,
                                    "enabled": true,
                                    "start-address": "10.10.10.1"
                                  },
                                  "dns-server": [
                                    {
                                      "address": "8.8.8.8"
                                    }
                                  ],
                                  "ip-version": "ipv4",
                                  "subnet-address": "10.10.10.0/24"
                                },
                                "name": "IP-t1"
                              }
                            ],
                            "name": "3node_sfc_nsh_nsd",
                            "short-name": "3node_sfc_nsh_nsd",
                            "vendor": "OSM",
                            "version": "1.0",
                            "vld": [
                              {
                                "id": "mgmtnet",
                                "mgmt-network": "true",
                                "name": "mgmtnet",
                                "short-name": "mgmtnet",
                                "type": "ELAN",
                                "vim-network-name": "provider",
                                "vnfd-connection-point-ref": [
                                  {
                                    "member-vnf-index-ref": 1,
                                    "vnfd-connection-point-ref": "vnf-mgmt",
                                    "vnfd-id-ref": "endpoint_vnfd"
                                  },
                                  {
                                    "member-vnf-index-ref": 2,
                                    "vnfd-connection-point-ref": "vnf-mgmt",
                                    "vnfd-id-ref": "endpoint_vnfd"
                                  }
                                ]
                              },
                              {
                                "id": "dataNet",
                                "ip-profile-ref": "IP-t1",
                                "name": "dataNet",
                                "short-name": "dataNet",
                                "type": "ELAN",
                                "vnfd-connection-point-ref": [
                                  {
                                    "ip-address": "10.10.10.11",
                                    "member-vnf-index-ref": 1,
                                    "vnfd-connection-point-ref": "vnf-data",
                                    "vnfd-id-ref": "endpoint_vnfd"
                                  },
                                  {
                                    "ip-address": "10.10.10.12",
                                    "member-vnf-index-ref": 2,
                                    "vnfd-connection-point-ref": "vnf-data",
                                    "vnfd-id-ref": "endpoint_vnfd"
                                  },
                                  {
                                    "member-vnf-index-ref": 3,
                                    "vnfd-connection-point-ref": "vnf-cp0",
                                    "vnfd-id-ref": "vnf_nsh_vnfd"
                                  }
                                ]
                              }
                            ],
                            "vnffgd": [
                              {
                                "classifier": [
                                  {
                                    "id": "class1",
                                    "match-attributes": [
                                      {
                                        "destination-ip-address": "10.10.10.12",
                                        "destination-port": 80,
                                        "id": "match1",
                                        "ip-proto": 6,
                                        "source-ip-address": "10.10.10.11",
                                        "source-port": 0
                                      }
                                    ],
                                    "member-vnf-index-ref": 1,
                                    "name": "class1-name",
                                    "rsp-id-ref": "rsp1",
                                    "vnfd-connection-point-ref": "vnf-data",
                                    "vnfd-id-ref": "endpoint_vnfd"
                                  }
                                ],
                                "description": "vnffg1-description",
                                "id": "vnffg1",
                                "name": "vnffg1-name",
                                "rsp": [
                                  {
                                    "id": "rsp1",
                                    "name": "rsp1-name",
                                    "vnfd-connection-point-ref": [
                                      {
                                        "member-vnf-index-ref": 3,
                                        "order": 0,
                                        "vnfd-egress-connection-point-ref": "vnf-cp0",
                                        "vnfd-id-ref": "vnf_nsh_vnfd",
                                        "vnfd-ingress-connection-point-ref": "vnf-cp0"
                                      }
                                    ]
                                  }
                                ],
                                "short-name": "vnffg1-sname",
                                "vendor": "vnffg1-vendor",
                                "version": "1.0"
                              }
                            ]
                          }
                        ]
                      }
                    }
                }"""
                )
            }

            return res

        } catch (RestClientException error) {

            println("RestClientException" + error)
            return null
        }
    }

    RestResponse NSInstantiation(String bearer, NSInstance nsInstance) {

        try {

            def res = restBuilder.post("$url/osm/nsilcm/v1/netslice_instances_content") {
                contentType("application/json")
                accept("application/json")
                header("Bearer", "$bearer")

                json(
                        nstId: "67c04681-1f11-41b6-bd13-7efd8a1ebceb",
                        nsiName: nsInstance.getName().toString(),
                        vimAccountId: "2eef36b0-82d2-441b-9285-85bcafe4b53f",
                        nsiDescription: nsInstance.getDescription().toString()
                )
            }

            return res

        } catch (RestClientException error) {

            println("RestClientException" + error)
            return null
        }
    }

    RestResponse delete_NSInstance(String refId) {

        try {

            def res = restBuilder.delete("$url/osm/nsilcm/v1/netslice_instances_content/${refId.trim()}") {
                contentType("application/json")
                accept("application/json")
                header("Bearer", "$bearer")
            }

            return res

        } catch (RestClientException error) {

            println("RestClientException" + error)
            return null
        }
    }
}