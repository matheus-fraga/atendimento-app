import { Button, Box, DataList, Center, ButtonGroup, Input } from "@chakra-ui/react"
import { CaseDialog } from "./CaseDialog"

const isUserAdmin = false; //habilita ou não painel de busca de casos por protocolo | cpf E filtro de todos os casos vs. casos do user autenticado

const stats = [
  { label: "New Users", value: "234", diff: -12, helpText: "Till date" },
  { label: "Sales", value: "£12,340", diff: 12, helpText: "Last 30 days" },
  { label: "Revenue", value: "3,450", diff: 4.5, helpText: "Last 30 days" },
]

export function Cases() {
  return (
      <Box>
        <Center p="4">Cases ({stats.length})</Center>
        <Center>
          {isUserAdmin && <ButtonGroup size="sm">
            <Button>All cases</Button>
            <Button>User's cases</Button>
            <Input size="sm" placeholder="protocol | document number"/> 
          </ButtonGroup>}
        </Center>
        <DataList.Root orientation="horizontal" divideY="1px" width="100%">
          {stats.map((item) => (
            <DataList.Item key={item.label}>
              <DataList.ItemLabel>{item.label}</DataList.ItemLabel>
              <DataList.ItemValue>{item.value}</DataList.ItemValue>
              <CaseDialog action="View" caseContext={item}/>
              <CaseDialog action="Edit" caseContext={item}/>
              <CaseDialog action="Delete" caseContext={item}/>
            </DataList.Item>
          ))}
        </DataList.Root>
      </Box>
    )
}