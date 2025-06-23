import { Button, CloseButton, Dialog, Portal, Field, Input  } from "@chakra-ui/react"
import { useEffect, useState } from "react";
import { UserForms } from "./UserForms"


export function UserDialog(props:any) {
  const [username, setUsername] = useState(props.userContext.username);
  const handleChange = (e:any) => {
    setUsername(e.target.value);
  };
  
  if(props.action == "View") {
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
                <Dialog.Title>User: {props.userContext.username}</Dialog.Title>
              </Dialog.Header>
              <Dialog.Body>
                
                <Field.Root disabled orientation="horizontal">
                  <Field.Label>Username</Field.Label>
                  <Input value= {props.userContext.username}/>
                </Field.Root>
                
                <Field.Root disabled orientation="horizontal">
                  <Field.Label>Role</Field.Label>
                  <Input value= {props.userContext.role}/>
                </Field.Root>
                
                <Field.Root disabled orientation="horizontal">
                  <Field.Label>Id</Field.Label>
                  <Input value= {props.userContext.id}/>
                </Field.Root>
                
              </Dialog.Body>
              <Dialog.CloseTrigger asChild>
                <CloseButton size="sm" />
              </Dialog.CloseTrigger>
            </Dialog.Content>
          </Dialog.Positioner>
        </Portal>
      </Dialog.Root>
    )
  } else if (props.action == "Edit"){
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
                <Dialog.Title>User: {props.userContext.username}</Dialog.Title>
                </Dialog.Header>
                <Dialog.Body>
                  
                  <Field.Root orientation="horizontal">
                    <Field.Label>Username</Field.Label>
                    <Input value={username} onChange={handleChange}/>
                  </Field.Root>
                  
                  <Field.Root disabled orientation="horizontal">
                    <Field.Label>Role</Field.Label>
                    <Input value= {props.userContext.role}/>
                  </Field.Root>
                  
                  <Field.Root disabled orientation="horizontal">
                    <Field.Label>Id</Field.Label>
                    <Input value= {props.userContext.id}/>
                  </Field.Root>
                  
                </Dialog.Body>
              <Dialog.Footer>
                <Dialog.ActionTrigger asChild>
                  <Button variant="outline">Cancel</Button>
                </Dialog.ActionTrigger>
                <Button>Update</Button>
              </Dialog.Footer>
              <Dialog.CloseTrigger asChild>
                <CloseButton size="sm" />
              </Dialog.CloseTrigger>
            </Dialog.Content>
          </Dialog.Positioner>
        </Portal>
      </Dialog.Root>
    )
  } else if (props.action == "Delete"){
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
                  {props.userContext.value}
                  {props.action} This action cannot be undone. This will permanently delete your
                  account and remove your data from our systems.
                </p>
              </Dialog.Body>
              <Dialog.Footer>
                <Dialog.ActionTrigger asChild>
                  <Button variant="outline">Cancel</Button>
                </Dialog.ActionTrigger>
                <Button colorPalette="red">Delete</Button>
              </Dialog.Footer>
              <Dialog.CloseTrigger asChild>
                <CloseButton size="sm" />
              </Dialog.CloseTrigger>
            </Dialog.Content>
          </Dialog.Positioner>
        </Portal>
      </Dialog.Root>
    )
  }
}
