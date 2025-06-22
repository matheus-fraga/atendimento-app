import { Button, Box, DataList, Center, ButtonGroup, Input } from "@chakra-ui/react"
import { CaseDialog } from "./CaseDialog"
import { useEffect, useState } from "react";
import { AlertComponent } from "./AlertComponent";

const isUserAdmin = false; //habilita ou não painel de busca de casos por protocolo | cpf E filtro de todos os casos vs. casos do user autenticado
export function Cases() {
  const [cases, setCases] = useState([]);
  const [loading, setLoading] = useState(true);
  const [integrationFailed, setIntegrationFailed] = useState(false);

  useEffect(() => {
      async function fetchCases() {
        try {
          const res = await fetch('/api/cases', {
          method: 'GET',
          credentials: 'include',
          headers: {
            'Content-Type': 'application/json'
          }
        });
          if (!res.ok) {
            throw new Error(`HTTP ${res.status}: ${res.statusText}`);
          }
          const data = await res.json();
          setCases(data);
        } catch (err) {
          console.error('Failed to fetch cases:', err);
          setIntegrationFailed(true);
        } finally {
          setLoading(false);
        }
      }

      fetchCases();
    }, []);
  if (loading || !cases) return (
      <Box>
        {integrationFailed && <AlertComponent isSuccess={false} msg="Failed to get cases."></AlertComponent>}
        {!integrationFailed && <div>Loading...</div>}
      </Box>
    );
  return (
      <Box>
        <Center p="4">Cases ({cases.length})</Center>
        <Center>
          {isUserAdmin && <ButtonGroup size="sm">
            <Button>All cases</Button>
            <Button>User's cases</Button>
            <Input size="sm" placeholder="protocol | document number"/> 
          </ButtonGroup>}
        </Center>
        <DataList.Root orientation="horizontal" divideY="1px" width="100%">
          {cases.map((item:any) => (
            <DataList.Item key={item.id}>
              <DataList.ItemLabel>{item.username}</DataList.ItemLabel>
              <DataList.ItemValue>{item.role}</DataList.ItemValue>
              <CaseDialog action="View" caseContext={item}/>
              <CaseDialog action="Edit" caseContext={item}/>
              <CaseDialog action="Delete" caseContext={item}/>
            </DataList.Item>
          ))}
        </DataList.Root>
      </Box>
    )
}