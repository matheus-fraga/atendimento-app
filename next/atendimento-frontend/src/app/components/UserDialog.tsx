import { Button, CloseButton, Dialog, Portal } from "@chakra-ui/react"
import { UserForms } from "./UserForms"


export function UserDialog(props) {
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
                <Dialog.Title>User: {props.userContext.label}</Dialog.Title>
              </Dialog.Header>
              <Dialog.Body>
                <p>
                  Details: {props.userContext.firstName}
                </p>
                <p>
                  Details: {props.userContext.lastName}
                </p>
                <p>
                  Details: {props.userContext.age}
                </p>
                <p>
                  Details: {props.userContext.email}
                </p>
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
                <Dialog.Title>{props.action} User</Dialog.Title>
              </Dialog.Header>
              <Dialog.Body>
                <UserForms action={props.action}></UserForms>
              </Dialog.Body>
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
