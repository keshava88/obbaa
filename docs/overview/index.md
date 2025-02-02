
<a id="overview" />

Overview
========

The Open Broadband-Broadband Access Abstraction (OB-BAA) is an open
source project that specifies the BAA layer\'s Northbound Interfaces 
(NBI), Core Components, Southbound Adapter Interfaces (SAI) and 
associated service elements for functions associated with the access 
network devices (e.g., configuration, reporting, alarms) that have 
been virtualized. Inherent in the OB-BAA project is the ability to 
pull differing access device types, including legacy implementations, 
together under a single network and service management and control 
umbrella to be exposed to management elements such as the 
SDN Management and/or Control, Element Management Systems and 
common Data Lakes. The 
OB-BAA project is designed to be deployed as one or more virtualized 
or containerized network functions (VNF/CNFs) that provide a reference 
implementation of the 
[BBF\'s Cloud CO BAA layer](https://www.broadband-forum.org/projects/virtualized-broadband/cloud-central-office).
Because the NBI, SAI and service elements utilize standardized data models 
and the BAA Core component and service elements are designed as virtualized 
micro-services with specified interfaces, the components of the OB-BAA 
project can also be adapted and deployed in other virtualized or 
non-virtualized environments.


System Functionality
====================

The high level functional diagram for the BAA layer is shown in the
figure below.
<p align="center">
 <img width="600px" height="300px" src="{{site.url}}/overview/system_description.png">
</p>

BAA Core
--------

The BAA Core provides functions needed manage and control Access PNFs,
either legacy or hardware disaggregated. These functions are defined
independent of the protocols used to communicate with the network
elements to the south or with the management and control elements to the
north. This independence is achieved by using standardized network
abstraction interfaces at the northbound and device adaptation
interfaces at the southbound edge of the BAA Core.

The Northbound Abstraction Interface (NAI) and SAI are combined with
protocol specific adapters and, in the case of SAI, also device specific
adapters to express the BAA NBIs and Soutbound Interfaces (SBI), i.e.
the external interfaces that allow the interaction with the SDN Manager
and Controller and the Access Nodes respectively.

The combination of the NBI/NAI--Core--SAI/SBI converts generic messages
from the SDN Management and Control (SDN M&C) elements into
protocol-specific/device-specific messages used to communicate with
external access devices and vice versa.

Southbound (SB) layer
---------------------

The Southbound layer (SB) contains device adapters that support
communication with the access devices in the network. These adapters may
be device specific in that they provide a low-level interface to device
hardware or accommodate vendor variations between devices.
Alternatively, they may be a generic device adapter designed to
interface with devices from multiple vendors. In either case, the device
adapter must comply with the SAI API which is the standards based
interface between the BAA Core and the access devices. This interface is
specified via the required data models and procedures, which in turn
govern the interworking between systems from different sources. Since
communication with specific a Access Node relies on device adapter, the
interface between the adapter and the Access Node is device specific and
is not considered a point of interworking. Device adapters may use
southbound protocol libraries provided as common resources, or they can
embed their own protocols as needed.

Southbound Adaptation Interface (SAI)
-------------------------------------

The SAI is specified via the required data models and procedures, which
in turn provide a first degree of adaptation between the Core and the
Access Nodes to the south.

Northbound (NB) layer
---------------------

Northbound the BAA layer communicates with one or more management and
control systems which may include access network managers, SDN managers
and controllers, and potentially directly with other management entities
such as the CloudCO\'s Domain Orchestrators. These systems may use
different protocols to communicate with functions in the BAA Core. The
BAA's NB layer exposes one or more NBIs with the appropriate protocol(s)
by applying protocol adapters at the NAI, the systems to the north can
be redefined and interfaces can be updated to use a different protocol
(e.g., RESTCONF or NETCONF) with minimum redesign.

Since the NBI relies on common protocol adapters, there is no analogy to
the device-specific interfaces at the SBI, and the behavior of the
protocol adapters should be defined by standards per each of the
applicable protocols. However, the data carried by the protocols is
specified at the abstraction interface.

Northbound Abstraction Interface (NAI)
--------------------------------------

The NAI is specified via the required data models and procedures. It
exposes a standardized abstract representation of access resources that
allows the interworking between the Core and the SDN Management and
Control elements to the north. For some deployment options it also
exposes standardized technology-specific interfaces to manage physical
layer and other AN specific attributes.

Performance Monitoring Service
------------------------------

The BAA layer provides the capability to collect performance monitoring 
(PM) data from managed pANs and then store the collected PM data as the 
corresponding standardized data model's PM YANG data elements.

The primary responsibility of the Performance Monitoring Service is to:
-	Collect PM data from managed devices.
-	Translate the PM data into the associated data elements defined by 
standardized pAN\'s YANG modules defined in the BBF\'s TR-413 
specification.
-	Store the translated data into a Data Lake accessible to SDN Management 
and Control (SDN M&C) elements for their specific functions 
(e.g., analysis, troubleshooting).

Control Relay Service
------------------------------
The Control Relay Service provides the capability to relay packets from an Access Node (AN) to an application endpoint in the  SDN Management and Control functional
layer.

vOMCI Function/vOMCI Proxy Services
------------------------------

The BAA layer provides the capability to manage ONU via vOMCI Services. The vOMCI Function provides translation capabilities between ONU YANG data models and the corresponding ITU-T G.988 OMCI messages. The vOMCI Proxy Service provides an aggregration and interception point betwen the OLT and the vOMCI Function.

BAA layer Deployed as an Actuator
---------------------------------

When deployed as an Actuator, the BAA layer acts as an aggregating
entity that provides actuation capabilities of control and management
functions expressed by northbound SDN M&C elements where the BAA layer
can be deployed as an executes the inputs received from the SDN M&C
element(s) and may act as a proxy of the data (flow information, PM and
alarm reports) received from the ANs.

The benefits of deploying BAA layer as an actuator is that the BAA layer
aggregates the connectivity of the ANs toward the SDN M&C and acts as
the authoritative source of truth and provides an always-on digital
representation for the ANs. In addition to providing the digital
representation of the AN, the BAA layer does proxy/relay selected data
from the ANs toward management elements (e.g., control, PM and alarms).
The determination of what data is relayed toward the SDN M&C elements is
dependent on the capabilities and characteristics of the BAA layer with
respect to the management and control architecture in which it is
deployed.

The figures below depicts the BAA layer deployed as an Actuator: the
M<sub>inf_L1</sub> interface that provides a vendor agnostic representation of
L1 management plane while the M<sub>inf\_L2-3</sub> and M<sub>fc\_L2-3</sub> interfaces
provide an abstract (i.e. vendor and technology agnostic) representation
of the L2-3 management and control plane. The distinction between
M<sub>inf\_L2-3</sub>/M<sub>fc\_L2-3</sub> and M<sub>inf\_L1</sub> interfaces enables the
separation of service provisioning tasks from network operation tasks.
The functionalities exposed by these interfaces are configurable with
out-of-the-box configurations that map to standardized Data Models
(e.g., TR-385 OLT, TR-301 DPU, TR-383 Firmware Management and TR-383
forwarding module).

In addition, both the M<sub>inf</sub> and M<sub>fc</sub> interfaces provide the capability
to transmit data from the AN toward the SDN M&C element. In some cases,
the BAA layer uses the data as it provides the AN digital
representations (e.g., operation states, current alarms, current PM)
while other times BAA layer acts as a relay encapsulating the data
without acting on the data in any way (e.g. DHCP snooping, IGMP
control). In all cases the data is presented in a vendor and technology
agnostic way toward the management systems for the type of access node.

<p align="center">
 <img width="350px" height="275px" src="{{site.url}}/overview/baa_actuator.png">
</p>

[<--Introduction](../index.md#introduction)

[Architecture -->](../architecture/index.md#architecture)