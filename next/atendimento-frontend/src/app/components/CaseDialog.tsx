"use client"
import { Button, CloseButton, Dialog, Portal, Field, Input, Center, Text } from "@chakra-ui/react";
import { CaseForms } from "./CaseForms";
import { useEffect, useState } from "react";

// Define the submitUpdate function without hooks.
async function submitUpdate(oldDescription: string, newDescription: string, protocolo:string) {
  console.log("submitUpdate");
  if (oldDescription === newDescription) {
    return null;
  }
  
  try {
    const res = await fetch('/api/editCase', {
      method: 'PATCH',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        novaDescricao: newDescription,
        protocol: protocolo
      }),
    });
    console.log(res);
    if (!res.ok) {
      throw new Error(`HTTP ${res.status}: ${res.statusText}`);
    }

    const data = await res.json();
    console.log(data);

  } catch (err) {
    console.error('Failed to fetch cases:', err);
    throw err;
  }
}

export function CaseDialog(props: any) {
  const [NewDescription, setNewDescription] = useState(props.caseContext.descricao);
  const [loading, setLoading] = useState(false);
  const [integrationFailed, setIntegrationFailed] = useState(false);

  const handleChange = (e: any) => {
    setNewDescription(e.target.value);
  };

  const handleUpdate = async () => {
    setLoading(true);
    setIntegrationFailed(false);

    try {
      await submitUpdate(props.caseContext.descricao, NewDescription, props.caseContext.protocolo);
    } catch (error) {
      setIntegrationFailed(true);
    } finally {
      setLoading(false);
    }
  };

  if (props.action === "View") {
    return (
      <Dialog.Root>
        <Dialog.Trigger asChild>
          <Button variant="outline" size="sm">
            {props.action}
          </Button>
        </Dialog.Trigger>
        <Portal>
          <Dialog.Backdrop />
          <Dialog.Positioner>
            <Dialog.Content>
              <Dialog.Header>
                <Dialog.Title>Protocolo: {props.caseContext.protocolo}</Dialog.Title>
              </Dialog.Header>
              <Dialog.Body>
                <Center h="50px">
                  <Text textStyle="lg">{props.caseContext.descricao?.toUpperCase() || ''}</Text>
                </Center>
                <Field.Root disabled orientation="horizontal">
                  <Field.Label>Protocolo</Field.Label>
                  <Input value={props.caseContext.protocolo} />
                </Field.Root>

                <Field.Root disabled orientation="horizontal">
                  <Field.Label>Nome do Cliente</Field.Label>
                  <Input value={props.caseContext.nomeCliente} />
                </Field.Root>

                <Field.Root disabled orientation="horizontal">
                  <Field.Label>Tipo</Field.Label>
                  <Input value={props.caseContext.tipo} />
                </Field.Root>

                <Field.Root disabled orientation="horizontal">
                  <Field.Label>Descrição</Field.Label>
                  <Input value={props.caseContext.descricao} />
                </Field.Root>

                <Field.Root disabled orientation="horizontal">
                  <Field.Label>Modificado em</Field.Label>
                  <Input value={props.caseContext.updatedAt} />
                </Field.Root>

                <Field.Root disabled orientation="horizontal">
                  <Field.Label>Criado em</Field.Label>
                  <Input value={props.caseContext.createdAt} />
                </Field.Root>
              </Dialog.Body>
              <Dialog.Footer>
                <Dialog.ActionTrigger asChild>
                  <Button variant="outline">Cancel</Button>
                </Dialog.ActionTrigger>
                <Button>Save</Button>
              </Dialog.Footer>
              <Dialog.CloseTrigger asChild>
                <CloseButton size="sm" />
              </Dialog.CloseTrigger>
            </Dialog.Content>
          </Dialog.Positioner>
        </Portal>
      </Dialog.Root>
    );
  } else if (props.action === "Edit") {
    return (
      <Dialog.Root>
        <Dialog.Trigger asChild>
          <Button variant="outline" size="sm">
            {props.action}
          </Button>
        </Dialog.Trigger>
        <Portal>
          <Dialog.Backdrop />
          <Dialog.Positioner>
            <Dialog.Content>
              <Dialog.Header>
                <Dialog.Title>{props.action} Atendimento {props.caseContext.label}</Dialog.Title>
              </Dialog.Header>
              <Dialog.Body>
                <Center h="50px">
                  <Text textStyle="lg">{props.caseContext.descricao?.toUpperCase() || ''}</Text>
                </Center>

                <Field.Root disabled orientation="horizontal">
                  <Field.Label>Tipo</Field.Label>
                  <Input value={props.caseContext.tipo} />
                </Field.Root>

                <Field.Root orientation="horizontal">
                  <Field.Label>Descrição</Field.Label>
                  <Input value={NewDescription} onChange={handleChange} />
                </Field.Root>

                <Field.Root disabled orientation="horizontal">
                  <Field.Label>Modificado em</Field.Label>
                  <Input value={props.caseContext.updatedAt} />
                </Field.Root>

                <Field.Root disabled orientation="horizontal">
                  <Field.Label>Criado em</Field.Label>
                  <Input value={props.caseContext.createdAt} />
                </Field.Root>
              </Dialog.Body>
              <Dialog.Footer>
                <Dialog.ActionTrigger asChild>
                  <Button variant="outline">Cancel</Button>
                </Dialog.ActionTrigger>
                <Button onClick={handleUpdate}>Update</Button>
              </Dialog.Footer>
              <Dialog.CloseTrigger asChild>
                <CloseButton size="sm" />
              </Dialog.CloseTrigger>
            </Dialog.Content>
          </Dialog.Positioner>
        </Portal>
      </Dialog.Root>
    );
  } else if (props.action === "Delete") {
    return (
      <Dialog.Root role="alertdialog">
        <Dialog.Trigger asChild>
          <Button variant="outline" size="sm">
            {props.action}
          </Button>
        </Dialog.Trigger>
        <Portal>
          <Dialog.Backdrop />
          <Dialog.Positioner>
            <Dialog.Content>
              <Dialog.Header>
                <Dialog.Title>Are you sure?</Dialog.Title>
              </Dialog.Header>
              <Dialog.Body>
                <p>
                  {props.caseContext.value}
                  {props.action} This action cannot be undone. This will
                  permanently delete your account and remove your data from our
                  systems.
                </p>
              </Dialog.Body>
              <Dialog.Footer>
                <Dialog.ActionTrigger asChild>
                  <Button variant="outline">Cancel</Button>
                </Dialog.ActionTrigger>
                <Button colorScheme="red">Delete</Button>
              </Dialog.Footer>
              <Dialog.CloseTrigger asChild>
                <CloseButton size="sm" />
              </Dialog.CloseTrigger>
            </Dialog.Content>
          </Dialog.Positioner>
        </Portal>
      </Dialog.Root>
    );
  }
}
