/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: O:\\working\\lanknightsshoutbox\\aidl\\com\\lanknights\\shoutbox\\NewShoutApi.aidl
 */
package com.lanknights.shoutbox;
public interface NewShoutApi extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.lanknights.shoutbox.NewShoutApi
{
private static final java.lang.String DESCRIPTOR = "com.lanknights.shoutbox.NewShoutApi";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.lanknights.shoutbox.NewShoutApi interface,
 * generating a proxy if needed.
 */
public static com.lanknights.shoutbox.NewShoutApi asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.lanknights.shoutbox.NewShoutApi))) {
return ((com.lanknights.shoutbox.NewShoutApi)iin);
}
return new com.lanknights.shoutbox.NewShoutApi.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_setLastProcessed:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setLastProcessed(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setLKLogin:
{
data.enforceInterface(DESCRIPTOR);
com.lanknights.shoutbox.LKLogin _arg0;
if ((0!=data.readInt())) {
_arg0 = com.lanknights.shoutbox.LKLogin.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.setLKLogin(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getCurrentMan:
{
data.enforceInterface(DESCRIPTOR);
com.lanknights.shoutbox.LKShoutManager _result = this.getCurrentMan();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_getShoutsNow:
{
data.enforceInterface(DESCRIPTOR);
this.getShoutsNow();
reply.writeNoException();
return true;
}
case TRANSACTION_getLatestShouts:
{
data.enforceInterface(DESCRIPTOR);
com.lanknights.shoutbox.ShoutParcel _result = this.getLatestShouts();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_addListener:
{
data.enforceInterface(DESCRIPTOR);
com.lanknights.shoutbox.NewShoutListener _arg0;
_arg0 = com.lanknights.shoutbox.NewShoutListener.Stub.asInterface(data.readStrongBinder());
this.addListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeListener:
{
data.enforceInterface(DESCRIPTOR);
com.lanknights.shoutbox.NewShoutListener _arg0;
_arg0 = com.lanknights.shoutbox.NewShoutListener.Stub.asInterface(data.readStrongBinder());
this.removeListener(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.lanknights.shoutbox.NewShoutApi
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public void setLastProcessed(int proc) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(proc);
mRemote.transact(Stub.TRANSACTION_setLastProcessed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setLKLogin(com.lanknights.shoutbox.LKLogin login) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((login!=null)) {
_data.writeInt(1);
login.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setLKLogin, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public com.lanknights.shoutbox.LKShoutManager getCurrentMan() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.lanknights.shoutbox.LKShoutManager _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurrentMan, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.lanknights.shoutbox.LKShoutManager.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void getShoutsNow() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getShoutsNow, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public com.lanknights.shoutbox.ShoutParcel getLatestShouts() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.lanknights.shoutbox.ShoutParcel _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLatestShouts, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.lanknights.shoutbox.ShoutParcel.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void addListener(com.lanknights.shoutbox.NewShoutListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void removeListener(com.lanknights.shoutbox.NewShoutListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_setLastProcessed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setLKLogin = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getCurrentMan = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getShoutsNow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getLatestShouts = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_addListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_removeListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
}
public void setLastProcessed(int proc) throws android.os.RemoteException;
public void setLKLogin(com.lanknights.shoutbox.LKLogin login) throws android.os.RemoteException;
public com.lanknights.shoutbox.LKShoutManager getCurrentMan() throws android.os.RemoteException;
public void getShoutsNow() throws android.os.RemoteException;
public com.lanknights.shoutbox.ShoutParcel getLatestShouts() throws android.os.RemoteException;
public void addListener(com.lanknights.shoutbox.NewShoutListener listener) throws android.os.RemoteException;
public void removeListener(com.lanknights.shoutbox.NewShoutListener listener) throws android.os.RemoteException;
}
