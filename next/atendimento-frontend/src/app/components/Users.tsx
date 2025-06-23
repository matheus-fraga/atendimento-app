import { Switch, Button, HStack, DataList, Box, Center, ButtonGroup, Input, Checkbox, Alert, CloseButton } from "@chakra-ui/react"
import { UserDialog } from "./UserDialog";
import { useEffect, useState } from "react";
import { AlertComponent } from "./AlertComponent";

const isUserAdmin = true; //habilita ou não painel de busca de casos por protocolo | cpf E filtro de todos os casos vs. casos do user autenticado

export function Users() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [integrationFailed, setIntegrationFailed] = useState(false);
  
  const [checked, setChecked] = useState(false)
  
  useEffect(() => {
      async function fetchUsers() {
        try {
          const res = await fetch('/api/users');
          if (!res.ok) {
            throw new Error(`HTTP ${res.status}: ${res.statusText}`);
          }
          const data = await res.json();
          setUsers(data);
          console.log(data);
        } catch (err) {
          console.error('Failed to fetch users:', err);
          setIntegrationFailed(true);
        } finally {
          setLoading(false);
        }
      }

      fetchUsers();
    }, []);
    if (loading || !users) return (
        <Box>
          {integrationFailed && <AlertComponent isSuccess={false} msg="Failed to get users."></AlertComponent>}
          {!integrationFailed && <div>Loading...</div>}
        </Box>
      );
  return (
      <Box>
        <Center p="4">Users ({users.length})</Center>
        <Center>
          {isUserAdmin && <ButtonGroup size="sm">
            <Button>All users</Button>
            <Button>Blocked users</Button>
            <Input size="sm" placeholder="protocol | document number"/> 
          </ButtonGroup>}
        </Center>
        <DataList.Root orientation="horizontal" divideY="1px" width="100%" height="100%">
          {users.map((item:any) => (
            <DataList.Item key={item.id}>
              <DataList.ItemValue>{item.username} {item.role}</DataList.ItemValue>
              
              <Switch.Root disabled={item.locked} checked={item.locked} onCheckedChange={(e) => setChecked(e.checked)}>
                <Switch.HiddenInput />
                <Switch.Control>
                  <Switch.Thumb />
                </Switch.Control>
                <Switch.Label >Is user blocked?</Switch.Label>
              </Switch.Root>

              <UserDialog action="View" userContext={item}/>
              <UserDialog action="Edit" userContext={item}/>
              <UserDialog action="Delete" userContext={item}/>
            </DataList.Item>
          ))}
        </DataList.Root>
      </Box>
    )
}